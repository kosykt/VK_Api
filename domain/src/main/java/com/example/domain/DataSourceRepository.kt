package com.example.domain

import com.example.domain.model.UseCaseResponse

interface DataSourceRepository {

    suspend fun getProfileInfo(token: String): UseCaseResponse
}