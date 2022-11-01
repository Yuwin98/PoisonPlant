package com.yuwin.proj.domain.model

data class ResponseState(
    val isLoading: Boolean = false,
    val data: Result = Result(),
    val error: String = ""
)
