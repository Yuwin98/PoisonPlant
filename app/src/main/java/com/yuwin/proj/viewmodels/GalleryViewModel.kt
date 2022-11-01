package com.yuwin.proj.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuwin.proj.domain.model.ImageFile
import com.yuwin.proj.util.IOUtil
import com.yuwin.proj.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val application: Application) :
    ViewModel() {


    private val _mediaFiles: MutableStateFlow<State<List<ImageFile>>> = MutableStateFlow(State.Idle)
    val mediaFiles: StateFlow<State<List<ImageFile>>> = _mediaFiles.asStateFlow()


    suspend fun readMediaFiles() {
        _mediaFiles.value = State.Loading
        val mediaFileList =
            IOUtil.readImageMediaFiles(application.applicationContext, viewModelScope)
        _mediaFiles.value = State.Success(mediaFileList)
    }


}