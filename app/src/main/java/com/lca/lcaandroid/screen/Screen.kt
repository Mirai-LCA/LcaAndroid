package com.lca.lcaandroid.screen

sealed class Screen(
    val route: String
) {
    data object Home: Screen("Home")
}