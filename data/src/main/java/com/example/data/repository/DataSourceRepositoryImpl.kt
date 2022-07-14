package com.example.data.repository

import com.example.data.toProfileInfoDomainModel
import com.example.domain.DataSourceRepository
import com.example.domain.model.UseCaseResponse

class DataSourceRepositoryImpl(
    private val networkDataSource: NetworkDataSource
): DataSourceRepository {

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
                else ->{
                    UseCaseResponse.Error("something wrong")
                }
            }
        }catch (e: Exception){
            return UseCaseResponse.Error(e.message.toString())
        }
    }
}