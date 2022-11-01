package com.yuwin.proj.navigation.composables

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.yuwin.proj.screens.ResultScreen
import com.yuwin.proj.util.Screens
import com.yuwin.proj.viewmodels.SharedViewModel

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.resultComposable(navController: NavController,sharedViewModel: SharedViewModel) {

    composable(Screens.Result.route) {
        ResultScreen(sharedViewModel = sharedViewModel) {
            navController.navigate(Screens.Home.route) {
                popUpTo(Screens.Home.route) { inclusive = true }
            }
        }
    }

}