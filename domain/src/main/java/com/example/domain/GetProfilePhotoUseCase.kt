package com.example.domain

import com.example.domain.model.UseCaseResponse

class GetProfilePhotoUseCase(
    private val repository: ProfileDataSourceRepository
) {
    suspend fun execute(): UseCaseResponse = repository.getProfilePhoto()
}