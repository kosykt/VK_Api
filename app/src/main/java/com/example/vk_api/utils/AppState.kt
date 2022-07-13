package com.example.vk_api.utils

sealed class AppState {
    data class Success<out T>(val data: T) : AppState()
    data class Error(val error: String) : AppState()
    object Loading : AppState()
}
