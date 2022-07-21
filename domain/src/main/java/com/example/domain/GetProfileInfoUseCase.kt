package com.example.domain

import com.example.domain.model.UseCaseResponse

class GetProfileInfoUseCase(
    private val repository: ProfileDataSourceRepository,
) {
    suspend fun execute(token: String): UseCaseResponse = repository.getProfileInfo(token)
}