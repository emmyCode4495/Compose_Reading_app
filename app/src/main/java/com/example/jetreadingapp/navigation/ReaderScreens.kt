package com.example.jetreadingapp.navigation

enum class ReaderScreens {
    ReaderBookDetailsScreen,
    ReaderHomeScreen,
    ReaderLoginScreen,
    ReaderSearchScreen,
    ReaderSplashScreen,
    ReaderBookStatsScreen,
    ReaderCreateAccountScreen,
    ReaderBookUpdateScreen;
    companion object {
        fun fromRoute(route: String): ReaderScreens
        = when(route.substringBefore("/"))
        {
            ReaderSplashScreen.name  -> ReaderSplashScreen
            ReaderLoginScreen.name -> ReaderLoginScreen
            ReaderCreateAccountScreen.name -> ReaderCreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            ReaderSearchScreen.name -> ReaderSearchScreen
            ReaderBookDetailsScreen.name -> ReaderBookDetailsScreen
            ReaderBookUpdateScreen.name -> ReaderBookUpdateScreen
            ReaderBookStatsScreen.name -> ReaderBookStatsScreen
            null -> ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognised")

        }    }


}