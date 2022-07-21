package com.example.data.repository

import com.example.data.network.profileinfomodel.ProfileInfoDTO
import com.example.data.network.profilephotomodel.ProfilePhotoDTO
import retrofit2.Response

interface NetworkDataSource {

    suspend fun getProfileInfo(token: String): Response<ProfileInfoDTO>
    suspend fun getProfilePhoto(token: String): Response<ProfilePhotoDTO>
}