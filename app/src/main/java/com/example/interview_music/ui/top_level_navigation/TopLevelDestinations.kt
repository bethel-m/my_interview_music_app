package com.example.interview_music.ui.top_level_navigation

import com.example.interview_music.ui.home.navigation.HomeScreenRoute
import com.example.interview_music.ui.search.navigation.SearchScreenRoute
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.interview_music.R

sealed class TopLevelDestinations<T>(
    @StringRes val title: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val iconDescription: Int,
    val route: T
) {
    data object Home : TopLevelDestinations<HomeScreenRoute>(
        title = R.string.home,
        selectedIcon = R.drawable.home,
        unselectedIcon = R.drawable.home_2,
        iconDescription = R.string.home_description,
        route = HomeScreenRoute
    )

    data object Search : TopLevelDestinations<SearchScreenRoute>(
        title = R.string.search,
        selectedIcon = R.drawable.search,
        unselectedIcon = R.drawable.search,
        iconDescription = R.string.search_description,
        route = SearchScreenRoute
    )

//    data object Favourites : TopLevelDestinations<String>(
//        title = R.string.favourites,
//        selectedIcon = R.drawable.favorite,
//        unselectedIcon = R.drawable.heart,
//        iconDescription = R.string.favourites_description,
//        route = "favourites"
//    )

    companion object {
        val destinations = listOf(Home, Search)
    }
}