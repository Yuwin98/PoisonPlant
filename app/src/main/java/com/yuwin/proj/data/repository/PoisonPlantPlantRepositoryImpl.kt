package com.yuwin.proj.data.repository

import com.yuwin.proj.data.remote.PoisonPlantApi
import com.yuwin.proj.data.remote.dto.DetectDto
import com.yuwin.proj.domain.repository.PoisonPlantRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class PoisonPlantPlantRepositoryImpl @Inject constructor(private val api: PoisonPlantApi): PoisonPlantRepository {
    override suspend fun detect(file: MultipartBody.Part): DetectDto {
        return api.detect(file)
    }
}