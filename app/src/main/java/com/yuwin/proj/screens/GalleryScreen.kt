package com.yuwin.proj.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yuwin.proj.domain.model.ImageFile
import com.yuwin.proj.util.ImageGrid
import com.yuwin.proj.util.Screens
import com.yuwin.proj.util.State
import com.yuwin.proj.viewmodels.GalleryViewModel
import com.yuwin.proj.viewmodels.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GalleryScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    galleryViewModel: GalleryViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        this.launch(Dispatchers.IO) {
            sharedViewModel.selectedImages.clear()
            galleryViewModel.readMediaFiles()
        }
    }

    val mediaFiles = galleryViewModel.mediaFiles.collectAsState()

    when (mediaFiles.value) {
        is State.Idle -> {
        }
        is State.Loading -> {
        }
        is State.Success -> {
            val data = (mediaFiles.value as State.Success<List<ImageFile>>).data

            ImageGrid(
                data = data,
                onSelect = { imageFile ->
                    sharedViewModel.selectedImages.add(imageFile)
                    navController.navigate(Screens.Result.route)
                })
        }
        else -> {}
    }


}