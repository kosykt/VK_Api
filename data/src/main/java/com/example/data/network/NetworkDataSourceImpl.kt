package com.example.data.network

import com.example.data.network.profileinfomodel.ProfileInfoDTO
import com.example.data.network.profilephotomodel.ProfilePhotoDTO
import com.example.data.repository.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NetworkDataSourceImpl(
    private val retrofitService: RetrofitService,
) : NetworkDataSource {

    override suspend fun getProfileInfo(token: String): Response<ProfileInfoDTO> {
        return withContext(Dispatchers.IO) { retrofitService.getProfileInfo(token) }
    }

    override suspend fun getProfilePhoto(
        token: String,
    ): Response<ProfilePhotoDTO> {
        return withContext(Dispatchers.IO) { retrofitService.getProfilePhoto(token) }
    }
}