package com.example.jetreadingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetreadingapp.screens.home.ReaderHomeScreen
import com.example.jetreadingapp.screens.login.ReaderLoginScreen
import com.example.jetreadingapp.screens.splashscreen.ReaderSplashScreen


@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
            startDestination = ReaderScreens.ReaderSplashScreen.name){

        composable(ReaderScreens.ReaderSplashScreen.name){
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name){
            ReaderHomeScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderLoginScreen.name){
            ReaderLoginScreen(navController = navController)
        }
    }

}