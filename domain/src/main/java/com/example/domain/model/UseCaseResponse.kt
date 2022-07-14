package com.example.domain.model

sealed class UseCaseResponse {
    data class Success<out T : DomainModel>(val data: T) : UseCaseResponse()
    data class Error(val message: String) : UseCaseResponse()
}
