package com.example.data.repository

import com.example.data.network.profileinfomodel.ProfileInfoDTO
import com.example.data.network.profilephotomodel.ProfilePhotoDTO
import com.example.data.network.saveprofileinfomodel.SaveProfileInfoDTO
import retrofit2.Response

interface NetworkDataSource {

    suspend fun getProfileInfo(): Response<ProfileInfoDTO>
    suspend fun getProfilePhoto(): Response<ProfilePhotoDTO>
    suspend fun postProfileInfoStatus(status: String): Response<SaveProfileInfoDTO>
    suspend fun postProfileInfoFirstName(firstName: String): Response<SaveProfileInfoDTO>
    suspend fun postProfileInfoLastName(lastName: String): Response<SaveProfileInfoDTO>
}