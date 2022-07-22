package com.example.data.repository

import com.example.data.network.saveprofileinfomodel.SaveProfileInfoDTO
import com.example.data.toProfileInfoDomainModel
import com.example.data.toProfilePhotoDomainModel
import com.example.domain.ProfileDataSourceRepository
import com.example.domain.model.SaveProfileInfoDomainModel
import com.example.domain.model.UseCaseResponse

class ProfileDataSourceRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
) : ProfileDataSourceRepository {

    override suspend fun getProfileInfo(): UseCaseResponse {
        try {
            val response = networkDataSource.getProfileInfo()
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

    override suspend fun getProfilePhoto(): UseCaseResponse {
        try {
            val response = networkDataSource.getProfilePhoto()
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

    override suspend fun postProfileInfoStatus(status: String): UseCaseResponse {
        try {
            val response = networkDataSource.postProfileInfoStatus(status)
            return when {
                response.isSuccessful && response.body() != null -> {
                    if (response.body()!!.response.changed == 1) {
                        UseCaseResponse.Success(SaveProfileInfoDomainModel(""))
                    } else {
                        UseCaseResponse.Error(response.body()!!.response.name_request?.lang.toString())
                    }
                }
                response.isSuccessful && response.body() == null -> {
                    UseCaseResponse.Error("response body is null")
                }
                else -> {
                    UseCaseResponse.Error("something wrong")
                }
            }
        }catch (e: Exception){
            return UseCaseResponse.Error(e.message.toString())
        }
    }

    override suspend fun postProfileInfoFirstName(firstName: String): UseCaseResponse {
        TODO("Not yet implemented")
    }

    override suspend fun postProfileInfoLastName(lastName: String): UseCaseResponse {
        TODO("Not yet implemented")
    }
}