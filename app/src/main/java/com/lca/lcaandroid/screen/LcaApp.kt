package com.lca.lcaandroid.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lca.lcaandroid.screen.home.HomeScreen

@Composable
fun LcaApp() {
    val navController = rememberNavController()
    LcaNavHost(navController)
}

@Composable
fun LcaNavHost(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}