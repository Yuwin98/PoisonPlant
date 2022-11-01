package com.yuwin.proj.domain.repository

import com.yuwin.proj.data.remote.dto.DetectDto
import okhttp3.MultipartBody
import retrofit2.http.Part

interface PoisonPlantRepository {

    suspend fun detect(file: MultipartBody.Part): DetectDto

}