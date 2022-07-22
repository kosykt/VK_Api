package com.example.domain

import com.example.domain.model.UseCaseResponse

interface ProfileDataSourceRepository {

    suspend fun getProfileInfo(): UseCaseResponse
    suspend fun getProfilePhoto(): UseCaseResponse
    suspend fun postProfileInfoStatus(status: String): UseCaseResponse
    suspend fun postProfileInfoFirstName(firstName: String): UseCaseResponse
    suspend fun postProfileInfoLastName(lastName: String): UseCaseResponse
}