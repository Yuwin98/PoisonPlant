package com.yuwin.proj.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.yuwin.proj.data.remote.dto.toResult
import com.yuwin.proj.domain.model.ImageFile
import com.yuwin.proj.domain.model.ResponseState
import com.yuwin.proj.domain.use_case.DetectPoisonPlantUseCase
import com.yuwin.proj.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val application: Application,
    private val detectPoisonPlantUseCase: DetectPoisonPlantUseCase
) : ViewModel() {


    val selectedImages = mutableListOf<ImageFile>()

    private val _image: MutableStateFlow<State<Bitmap>> = MutableStateFlow(State.Idle)
    val image: StateFlow<State<Bitmap>> = _image.asStateFlow()

    private val _responseState = mutableStateOf(ResponseState())
    val responseState = _responseState


    fun loadImage(uri: Uri) {
        _image.value = State.Loading
        val bitmap = Glide.with(application.applicationContext)
            .asBitmap()
            .load(uri)
            .submit()
            .get()
        _image.value = State.Success(bitmap)
    }

    fun detect(image: Bitmap) {
        detectPoisonPlantUseCase.invoke(createRequestBody(image)).onEach { result ->
            when (result) {
                is State.Success -> {
                    _responseState.value = ResponseState(data = result.data.toResult())
                }
                is State.Error -> {
                    _responseState.value = ResponseState(error = result.message)
                }
                is State.Loading -> {
                    _responseState.value = ResponseState(isLoading = true)
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }
    private fun createRequestBody(image: Bitmap): MultipartBody.Part {
        val context = application.applicationContext

        val filename = "${UUID.randomUUID()}.jpeg"
        val file = File(context.cacheDir, filename)
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitmapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, reqFile)
    }
}

