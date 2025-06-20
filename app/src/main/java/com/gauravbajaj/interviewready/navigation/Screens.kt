package com.gauravbajaj.interviewready.navigation

import com.gauravbajaj.interviewready.data.model.User

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details/{id}") {
        fun createRoute(user: User) = "details/${user.id}"
    }
}
