package com.yuwin.proj.util

import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.yuwin.proj.domain.model.ImageFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object IOUtil {

    private suspend fun readImageFiles(
        context: Context,
        viewModelScope: CoroutineScope
    ): MutableList<ImageFile> {
        val imageList = mutableListOf<ImageFile>()

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_ADDED,
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            sortOrder
        )

        viewModelScope.launch(Dispatchers.IO) {
            query?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                val albumName =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)
                val createdAtColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_ADDED)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(albumName)
                    val createdAt = cursor.getString(createdAtColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    imageList += ImageFile(contentUri, name, createdAt)
                }
            }
        }.join()
        return imageList
    }


    suspend fun readImageMediaFiles(
        context: Context,
        viewModelScope: CoroutineScope
    ): MutableList<ImageFile> {
        return readImageFiles(context, viewModelScope)
    }


}