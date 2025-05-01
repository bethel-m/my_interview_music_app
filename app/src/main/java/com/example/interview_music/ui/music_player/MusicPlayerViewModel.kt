package com.example.interview_music.ui.music_player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.interview_music.data.dataStoreCache.LastPlayedTrack
import com.example.interview_music.data.player.PlaybackService
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.data.tracks.repository.LocalTracksRepository
import com.example.interview_music.ui.music_player.navigation.MusicPlayerRoute
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_TIMEOUT = 5000L


@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val localRepository: LocalTracksRepository,
    private val lastPlayedTrack: LastPlayedTrack,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private var mediaController: MediaController? = null
    private val _trackData = MutableStateFlow(TrackData())
    private val _sliderPosition = MutableStateFlow(0f)
    private val _isOnRepeat = MutableStateFlow(false)
    private val _isPlaying = MutableStateFlow(false)
    private val _errorOccurred = MutableStateFlow(false)
    private val _isOnShuffle = MutableStateFlow(false)

    // Expose flows to UI
    val trackData: StateFlow<TrackData> = _trackData
    val sliderPosition: StateFlow<Float> = _sliderPosition
    val isOnRepeat: StateFlow<Boolean> = _isOnRepeat
    val isPlaying: StateFlow<Boolean> = _isPlaying
    val errorOccurred: StateFlow<Boolean> = _errorOccurred
    val isOnShuffle: StateFlow<Boolean> = _isOnShuffle


    var streamTracks by mutableStateOf<List<Track>>(emptyList())
        private set

    fun addStreamTracks(tracks: List<Track>) {
        streamTracks = tracks
    }

    // In your ViewModel or a Composable-side effect
    fun startPlaybackService(context: Context) {
        val intent = Intent(context, PlaybackService::class.java)

        // For Android 8+, start as a foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
        connectToService()
    }

    private fun connectToService() {
        val sessionToken = SessionToken(
            context,
            ComponentName(context, PlaybackService::class.java)
        )
        val controlFuture = MediaController.Builder(context, sessionToken)
            .buildAsync()
        controlFuture.addListener(
            {
                mediaController = controlFuture.get()
                setupMediaControllerListeners()
            },
            MoreExecutors.directExecutor()
        )
    }

    private fun setupMediaControllerListeners() {
        mediaController?.addListener(object : Player.Listener {

            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                val isIdle = player.mediaItemCount == 0 &&
                        !player.isPlaying


            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                updateTrackData(mediaItem)
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                _isOnShuffle.value = shuffleModeEnabled
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                _isOnRepeat.value = repeatMode == Player.REPEAT_MODE_ONE
            }

            override fun onPlayerError(error: PlaybackException) {
                _errorOccurred.value = true
            }
        })
        updateSliderPosition()
    }

    // --- Player Controls ---
    fun playSong(musicInfo: MusicPlayerRoute) {
        viewModelScope.launch {
            val track = localRepository.getTrackById(musicInfo.selectedTrackId ?: 0)
            track.onSuccess { track ->
                val mediaItem = addMetaData(track)
                mediaController?.setMediaItem(mediaItem)
                mediaController?.prepare()
                mediaController?.play()
               loadResumedTracks()
            }
            track.onFailure { _errorOccurred.value = true }
        }
    }
    fun streamOnlineSongs(){
       if(streamTracks.isNotEmpty()){
       mediaController?.clearMediaItems()
            val mediaItem = addMetaData(streamTracks[0])
            mediaController?.setMediaItem(mediaItem)
            mediaController?.prepare()
            mediaController?.play()
            addToPlayList(streamTracks.map { it.id })
        }
    }

    fun togglePlayPause() {
        if (_isPlaying.value) {
            mediaController?.pause()
        } else {
            mediaController?.play()
        }
    }

    fun nextTrack() {
        mediaController?.seekToNext()
    }

    fun previousTrack() {
        mediaController?.seekToPrevious()
    }

    fun repeatTrack() {
        mediaController?.repeatMode = if (_isOnRepeat.value) {
            Player.REPEAT_MODE_OFF
        } else {
            Player.REPEAT_MODE_ONE
        }
    }

    fun shufflePlaylist() {
        mediaController?.shuffleModeEnabled = !_isOnShuffle.value
    }

    // --- Slider Controls ---
    private fun updateSliderPosition() {
        viewModelScope.launch {
            while (true) {
                mediaController?.currentPosition?.let { pos ->
                    _sliderPosition.value = pos.toFloat()
                }
                delay(100L)
            }
        }
    }

    fun seekTo(position: Long) {
        mediaController?.seekTo(position)
    }

    // --- Metadata & Playlist ---
    private fun updateTrackData(mediaItem: MediaItem?) {
        val data = TrackData(
            songId = mediaItem?.mediaMetadata?.trackNumber,
            songTitle = mediaItem?.mediaMetadata?.title.toString(),
            artistName = mediaItem?.mediaMetadata?.artist.toString(),
            songCoverArt = mediaItem?.mediaMetadata?.description.toString(),
            duration = mediaItem?.mediaMetadata?.durationMs
        )
        _trackData.value = data
        data.songId?.let { saveLastPlayedId(it) }
    }

    fun addToPlayList(ids: List<Int>) {
        viewModelScope.launch {
            ids.forEach { id ->
                localRepository.getTrackById(id).onSuccess { track ->
                    mediaController?.addMediaItem(addMetaData(track))
                }.onFailure { _errorOccurred.value = true }
            }
        }
    }

    // --- Cleanup ---
    override fun onCleared() {
        mediaController?.release()
        super.onCleared()
    }

    val lastPlayedId: StateFlow<Int?> =
        lastPlayedTrack.lastPlayedId
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(DEFAULT_TIMEOUT),
                initialValue = null
            )


    fun resumeLastPlayedTrack(id: Int) {

        viewModelScope.launch {
            while (mediaController?.isPlaying == null) {
                delay(100L)
            }
            if (
                mediaController?.mediaItemCount
                == 0 &&
                mediaController?.isPlaying == false
            ) {
                println("${mediaController?.mediaItemCount}--${mediaController?.isPlaying}++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                println("${mediaController?.mediaItemCount}--${mediaController?.isPlaying}++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                println("${mediaController?.mediaItemCount}--${mediaController?.isPlaying}++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                println("${mediaController?.mediaItemCount}--${mediaController?.isPlaying}++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                val trackResult = localRepository.getTrackById(id)
                trackResult.onSuccess { track ->
                    addListeners()
                    val item = addMetaData(track = track)
                    mediaController?.setMediaItem(item)
                    mediaController?.prepare()
                    mediaController?.pause()
                    _isPlaying.value = false
                    updateSliderPosition()
                    loadResumedTracks()
                }
                trackResult.onFailure {
                    _errorOccurred.value = true
                }
            }
        }
    }

    suspend fun loadResumedTracks() {
        val playList = localRepository.fetchAllTracks()
        playList.onSuccess {
            addToPlayList(ids = it.map { it.id })
        }
        playList.onFailure {
            _errorOccurred.value = true
        }
    }

    fun saveLastPlayedId(id: Int) {
        viewModelScope.launch { lastPlayedTrack.saveLastPlayedId(id) }
    }

    fun onSliderPositionChange(value: Float) {
        _sliderPosition.value = value
    }

    fun onSliderPositionChangeDone() {
        seekTo(_sliderPosition.value.toLong())
    }
//
//    fun updateSliderPosition() {
//        viewModelScope.launch {
//            while (true) {
//                try {
//                    val position = player.currentPosition
//                    _sliderPosition.value = position.toFloat()
//                    delay(100L)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }
//
//    fun addToPlayList(ids: List<Int> = emptyList()) {
//        viewModelScope.launch {
//            ids.forEach { id ->
//                val track = localRepository.getTrackById(id)
//                track.onSuccess { track ->
//                    //    val mediaItem = MediaItem.fromUri(track.audio)
//                    val item = addMetaData(track = track)
//                    player.addMediaItem(item)
//                }
//                track.onFailure {
//                    _errorOccurred.value = true
//                }
//
//            }
//        }
//
//    }

    fun onShowError() {
        _errorOccurred.value = false
    }

    fun addMetaData(track: Track): MediaItem {
        val durationSeconds = track.duration
        val durationMs: Long = durationSeconds * 1000L
        val newMetadata = MediaMetadata.Builder()
            .setTitle(track.name)
            .setArtist(track.artistName)
            .setDurationMs(durationMs)
            .setDescription(track.image)
            .setTrackNumber(track.id)
            .build()

        // Now create a new MediaItem with the updated metadata.
        return MediaItem.Builder()
            //.setMediaId(mediaItem.mediaId)
            .setUri(track.audio)
            .setMediaMetadata(newMetadata) // Set the new metadata
            .build()

        //  return mediaItem
    }


    fun stopPlaying() {
        mediaController?.stop()
    }


    fun seekForwardTo(position: Long) {
        _sliderPosition.value = _sliderPosition.value + position.toFloat()
        seekTo(_sliderPosition.value.toLong())
    }

    fun seekBackTo(position: Long) {
        if (_sliderPosition.value - position.toFloat() < 0) {
            _sliderPosition.value = 0f
        } else {
            _sliderPosition.value = _sliderPosition.value - position.toFloat()
        }
        seekTo(_sliderPosition.value.toLong())
    }


    fun addListeners() {

        mediaController?.addListener(
            object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    val cause = error.cause
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    if (isPlaying) {
                        // Active playback.
                    } else {
                        // Not playing because playback is paused, ended, suppressed, or the player
                        // is buffering, stopped or failed. Check player.playWhenReady,
                        // player.playbackState, player.playbackSuppressionReason and
                        // player.playerError for details.
                    }
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    val trackData = TrackData(
                        songTitle = mediaItem?.mediaMetadata?.title.toString(),
                        artistName = mediaItem?.mediaMetadata?.artist.toString(),
                        songCoverArt = mediaItem?.mediaMetadata?.description.toString(),
                        duration = mediaItem?.mediaMetadata?.durationMs,
                        songId = mediaItem?.mediaMetadata?.trackNumber,
                    )
                    if (trackData.songId != null) {
                        saveLastPlayedId(trackData.songId)
                    }
                    _trackData.value = trackData
                }

                override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                    super.onMediaMetadataChanged(mediaMetadata)
                }
            }

        )

    }

    fun stopShowingError(){
        _errorOccurred.value = false
    }
//    override fun onCleared() {
//        super.onCleared()
//        player.release()
//    }
}


data class TrackData(
    val songId: Int? = null,
    val songTitle: String = "",
    val artistName: String = "",
    val songCoverArt: String = "",
    val duration: Long? = null,
)