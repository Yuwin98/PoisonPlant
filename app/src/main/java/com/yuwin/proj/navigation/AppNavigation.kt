package com.yuwin.proj.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.yuwin.proj.navigation.composables.galleryComposable
import com.yuwin.proj.navigation.composables.homeComposable
import com.yuwin.proj.navigation.composables.resultComposable
import com.yuwin.proj.util.Screens
import com.yuwin.proj.viewmodels.SharedViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {

    val navController = rememberAnimatedNavController()

    val sharedViewModel = viewModel(modelClass = SharedViewModel::class.java)

    AnimatedNavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        homeComposable(navController)
        galleryComposable(navController, sharedViewModel)
        resultComposable(navController, sharedViewModel)
    }
}