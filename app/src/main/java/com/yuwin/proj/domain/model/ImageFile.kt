package com.yuwin.proj.domain.model

import android.net.Uri

data class ImageFile(
    val uri: Uri,
    val albumName: String,
    val createdAt: String
)