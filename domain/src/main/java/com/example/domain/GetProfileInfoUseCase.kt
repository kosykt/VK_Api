package com.example.domain

import com.example.domain.model.UseCaseResponse

class GetProfileInfoUseCase(
    private val repository: ProfileDataSourceRepository,
) {
    suspend fun execute(): UseCaseResponse = repository.getProfileInfo()
}