package com.yuwin.proj.domain.use_case

import android.util.Log
import com.yuwin.proj.data.remote.dto.DetectDto
import com.yuwin.proj.domain.repository.PoisonPlantRepository
import com.yuwin.proj.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DetectPoisonPlantUseCase @Inject constructor(private val repository: PoisonPlantRepository) {

    operator fun invoke(image: MultipartBody.Part): Flow<State<DetectDto>> =
        flow {
            try {
                emit(State.Loading)
                val uploadFiles = repository.detect(image)
                emit(State.Success(uploadFiles))
            } catch (e: HttpException) {
                emit(
                    State.Error(message = e.localizedMessage ?: "Unexpected error occurred")
                )
            } catch (e: IOException) {
                emit(State.Error(message = "Couldn't Reach server. Check Internet Connection"))
            }
        }.flowOn(Dispatchers.IO)
}