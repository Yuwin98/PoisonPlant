package com.yuwin.proj.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.yuwin.proj.domain.model.Result

data class DetectDto(
    @SerializedName("class")
    val classX: String,
    @SerializedName("confidence")
    val confidence: Float
)

fun DetectDto.toResult(): Result {
    return Result(className = classX, confidence = this.confidence)
}