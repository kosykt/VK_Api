package com.example.domain

import com.example.domain.model.UseCaseResponse

class PostProfileInfoLastNameUseCase(
    private val repository: ProfileDataSourceRepository,
) {
    suspend fun execute(lastName: String): UseCaseResponse = repository.postProfileInfoLastName(lastName)
}