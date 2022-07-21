package com.example.data.repository

import com.example.data.toProfileInfoDomainModel
import com.example.data.toProfilePhotoDomainModel
import com.example.domain.ProfileDataSourceRepository
import com.example.domain.model.UseCaseResponse

class ProfileDataSourceRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
) : ProfileDataSourceRepository {

    override suspend fun getProfileInfo(token: String): UseCaseResponse {
        try {
            val response = networkDataSource.getProfileInfo(token)
            return when {
                response.isSuccessful && response.body() != null -> {
                    UseCaseResponse.Success(response.body()!!.toProfileInfoDomainModel())
                }
                response.isSuccessful && response.body() == null -> {
                    UseCaseResponse.Error("response body is null")
                }
                else -> {
                    UseCaseResponse.Error("something wrong")
                }
            }
        } catch (e: Exception) {
            return UseCaseResponse.Error(e.message.toString())
        }
    }

    override suspend fun getProfilePhoto(token: String): UseCaseResponse {
        try {
            val response = networkDataSource.getProfilePhoto(token)
            return when {
                response.isSuccessful && response.body() != null -> {
                    UseCaseResponse.Success(response.body()!!.toProfilePhotoDomainModel())
                }
                response.isSuccessful && response.body() == null -> {
                    UseCaseResponse.Error("response body is null")
                }
                else -> {
                    UseCaseResponse.Error("something wrong")
                }
            }
        } catch (e: Exception) {
            return UseCaseResponse.Error(e.message.toString())
        }
    }
}