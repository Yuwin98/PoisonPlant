package com.yuwin.proj.navigation.composables

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.yuwin.proj.screens.HomeScreen
import com.yuwin.proj.util.Screens

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeComposable(navController: NavController) {

    composable(Screens.Home.route) {
        HomeScreen {
            navController.navigate(Screens.Gallery.route)
        }
    }

}