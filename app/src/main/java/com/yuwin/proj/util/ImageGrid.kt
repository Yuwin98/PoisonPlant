package com.yuwin.proj.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import com.yuwin.proj.domain.model.ImageFile
import com.yuwin.proj.util.Constants.DEFAULT_GALLERY_IMAGE_SIZE

const val GALLERY_CONTENT_GAP = 16
const val GALLERY_ITEM_ROW_COUNT = 3f
const val GALLERY_IMAGE_THUMBNAIL_SIZE = 128
val GALLERY_CONTENT_PADDING = 0.5.dp
val MIN_PADDING = 0.6.dp

@Composable
fun ImageGrid(
    modifier: Modifier = Modifier,
    data: List<ImageFile>,
    onSelect: (ImageFile) -> Unit
) {

    val screenWidthDp =
        (LocalConfiguration.current.screenWidthDp - GALLERY_CONTENT_GAP) / GALLERY_ITEM_ROW_COUNT

    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = GALLERY_CONTENT_PADDING),
        columns = GridCells.Adaptive(screenWidthDp.dp)
    ) {

        items(data) { item ->
            Box(
                modifier = Modifier
                    .padding(MIN_PADDING)
                    .size(screenWidthDp.dp)
                    .clickable(enabled = true) {
                        onSelect(item)
                    }
            ) {
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = item.uri,
                    requestOptions = {
                        RequestOptions()
                            .override(DEFAULT_GALLERY_IMAGE_SIZE)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}