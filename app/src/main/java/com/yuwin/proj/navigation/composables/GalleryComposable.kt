package com.yuwin.proj.navigation.composables

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.yuwin.proj.screens.GalleryScreen
import com.yuwin.proj.util.Screens
import com.yuwin.proj.viewmodels.SharedViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.galleryComposable(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {

    composable(route = Screens.Gallery.route,

        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            fadeIn()
        },
        popExitTransition = {
            fadeOut()
        }) {
        GalleryScreen(navController = navController, sharedViewModel = sharedViewModel )
    }

}