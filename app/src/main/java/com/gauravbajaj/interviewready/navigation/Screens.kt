package com.gauravbajaj.interviewready.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details/{id}") {
        fun createRoute(id: String) = "details/$id"
    }
}
