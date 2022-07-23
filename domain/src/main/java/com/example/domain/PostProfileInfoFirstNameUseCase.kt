package com.example.domain

import com.example.domain.model.UseCaseResponse

class PostProfileInfoFirstNameUseCase(
    private val repository: ProfileDataSourceRepository,
) {
    suspend fun execute(firstName: String): UseCaseResponse = repository.postProfileInfoFirstName(firstName)
}