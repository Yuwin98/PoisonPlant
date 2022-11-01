package com.yuwin.proj.data.remote

import com.yuwin.proj.data.remote.dto.DetectDto
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PoisonPlantApi {

    @POST("predict")
    @Multipart
    suspend fun detect(
        @Part file: MultipartBody.Part
    ): DetectDto

}