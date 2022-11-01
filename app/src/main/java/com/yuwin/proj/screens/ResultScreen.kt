package com.yuwin.proj.screens

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.yuwin.proj.domain.model.ResponseState
import com.yuwin.proj.util.State
import com.yuwin.proj.viewmodels.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ResultScreen(sharedViewModel: SharedViewModel, home: () -> Unit) {

    LaunchedEffect(key1 = Unit) {
        this.launch(Dispatchers.IO) {
            sharedViewModel.responseState.value = ResponseState()
            sharedViewModel.loadImage(sharedViewModel.selectedImages.first().uri)
        }
    }

    val image = sharedViewModel.image.collectAsState()

    Box(
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {

        when (image.value) {
            is State.Idle -> {
            }
            is State.Loading -> {
            }
            is State.Success -> {
                val bitmap = (image.value as State.Success<Bitmap>).data

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop,
                        imageModel = bitmap
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    val state = sharedViewModel.responseState.value
                    if (state.data.className.isNotEmpty()) {
                        Button(onClick = home) {
                            Text(text = "Home")
                        }
                    } else {
                        Button(onClick = { sharedViewModel.detect(bitmap) }) {
                            Text(text = "Detect")
                        }
                    }

                    val columnWeight = 0.5f
                    if (!state.isLoading) {
                        LazyColumn(
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            if (state.data.className.isNotEmpty()) {
                                item {
                                    Row(Modifier.background(Color.White)) {
                                        TableCell(text = "Class", weight = columnWeight)
                                        TableCell(text = "Confidence", weight = columnWeight)
                                    }
                                }
                                val dataList = listOf(state.data)
                                items(dataList) { data ->
                                    Row(Modifier.fillMaxWidth()) {
                                        TableCell(text = data.className, weight = columnWeight)
                                        TableCell(
                                            text = data.confidence.toString(),
                                            weight = columnWeight
                                        )
                                    }
                                }
                            }
                        }


                    } else {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.weight(1f)
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colors.primary)
                        }
                    }

                }


            }
            else -> {}
        }


    }

}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}