package com.example.hexadecimalcolor.common.utils

sealed class LoadingState {
    object Loading: LoadingState()
    data class Success(val data: Map<String, String>): LoadingState()
    data class Failure(val error: String?): LoadingState()
}