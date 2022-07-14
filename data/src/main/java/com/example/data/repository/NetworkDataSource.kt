package com.example.data.repository

import com.example.data.network.profileinfomodel.ProfileInfoDTO
import retrofit2.Response

interface NetworkDataSource {

    suspend fun getProfileInfo(token: String): Response<ProfileInfoDTO>
}