package com.example.domain

import com.example.domain.model.UseCaseResponse

class PostProfileInfoStatusUseCase(
    private val repository: ProfileDataSourceRepository,
) {
    suspend fun execute(status: String): UseCaseResponse = repository.postProfileInfoStatus(status)
}