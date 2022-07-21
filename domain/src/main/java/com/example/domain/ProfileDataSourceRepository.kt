package com.example.domain

import com.example.domain.model.UseCaseResponse

interface ProfileDataSourceRepository {

    suspend fun getProfileInfo(token: String): UseCaseResponse
    suspend fun getProfilePhoto(token: String): UseCaseResponse
}