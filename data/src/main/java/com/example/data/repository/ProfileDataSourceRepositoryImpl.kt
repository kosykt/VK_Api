package com.example.data.repository

import com.example.data.network.saveprofileinfomodel.SaveProfileInfoDTO
import com.example.data.toProfileInfoDomainModel
import com.example.data.toProfilePhotoDomainModel
import com.example.domain.ProfileDataSourceRepository
import com.example.domain.model.SaveProfileInfoDomainModel
import com.example.domain.model.UseCaseResponse
import retrofit2.Response

class ProfileDataSourceRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
) : ProfileDataSourceRepository {

    override suspend fun getProfileInfo(): UseCaseResponse {
        return try {
            val response = networkDataSource.getProfileInfo()
            when {
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
            UseCaseResponse.Error(e.message.toString())
        }
    }

    override suspend fun getProfilePhoto(): UseCaseResponse {
        return try {
            val response = networkDataSource.getProfilePhoto()
            when {
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
            UseCaseResponse.Error(e.message.toString())
        }
    }

    override suspend fun postProfileInfoStatus(status: String): UseCaseResponse {
        return try {
            postInfoResponseHandling(networkDataSource.postProfileInfoStatus(status))
        } catch (e: Exception) {
            UseCaseResponse.Error(e.message.toString())
        }
    }

    override suspend fun postProfileInfoFirstName(firstName: String): UseCaseResponse {
        return try {
            postInfoResponseHandling(networkDataSource.postProfileInfoFirstName(firstName))
        } catch (e: Exception) {
            UseCaseResponse.Error(e.message.toString())
        }
    }

    override suspend fun postProfileInfoLastName(lastName: String): UseCaseResponse {
        return try {
            postInfoResponseHandling(networkDataSource.postProfileInfoLastName(lastName))
        } catch (e: Exception) {
            UseCaseResponse.Error(e.message.toString())
        }
    }

    private fun postInfoResponseHandling(response: Response<SaveProfileInfoDTO>): UseCaseResponse {
        return when {
            response.isSuccessful && response.body() != null -> {
                if (response.body()?.response?.changed == 1) {
                    UseCaseResponse.Success(SaveProfileInfoDomainModel(""))
                } else if (response.body()?.response?.changed == 0 && response.body()?.response?.name_request == null) {
                    UseCaseResponse.Success(SaveProfileInfoDomainModel(""))
                } else if (response.body()?.response?.changed == 0 && response.body()?.response?.name_request != null) {
                    UseCaseResponse.Error(response.body()?.response?.name_request?.lang.orEmpty())
                } else {
                    UseCaseResponse.Error("Empty error message")
                }
            }
            response.isSuccessful && response.body() == null -> {
                UseCaseResponse.Error("response body is null")
            }
            else -> {
                UseCaseResponse.Error("something wrong")
            }
        }
    }
}