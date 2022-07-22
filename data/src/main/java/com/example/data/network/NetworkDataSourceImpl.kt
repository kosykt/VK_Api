package com.example.data.network

import com.example.data.network.profileinfomodel.ProfileInfoDTO
import com.example.data.network.profilephotomodel.ProfilePhotoDTO
import com.example.data.network.saveprofileinfomodel.SaveProfileInfoDTO
import com.example.data.repository.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NetworkDataSourceImpl(
    private val retrofitService: RetrofitService,
) : NetworkDataSource {

    override suspend fun getProfileInfo(): Response<ProfileInfoDTO> {
        return withContext(Dispatchers.IO) { retrofitService.getProfileInfo() }
    }

    override suspend fun getProfilePhoto(): Response<ProfilePhotoDTO> {
        return withContext(Dispatchers.IO) { retrofitService.getProfilePhoto() }
    }

    override suspend fun postProfileInfoStatus(status: String): Response<SaveProfileInfoDTO> {
        return withContext(Dispatchers.IO) { retrofitService.postProfileInfoStatus(status) }
    }

    override suspend fun postProfileInfoFirstName(firstName: String): Response<SaveProfileInfoDTO> {
        return withContext(Dispatchers.IO) { retrofitService.postProfileInfoFirstName(firstName) }
    }

    override suspend fun postProfileInfoLastName(lastName: String): Response<SaveProfileInfoDTO> {
        return withContext(Dispatchers.IO) { retrofitService.postProfileInfoLastName(lastName) }
    }
}