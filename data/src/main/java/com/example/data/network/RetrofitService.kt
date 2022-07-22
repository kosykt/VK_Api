package com.example.data.network

import com.example.data.network.profileinfomodel.ProfileInfoDTO
import com.example.data.network.profilephotomodel.ProfilePhotoDTO
import com.example.data.network.saveprofileinfomodel.SaveProfileInfoDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {

    @GET("/method/account.getProfileInfo")
    suspend fun getProfileInfo(): Response<ProfileInfoDTO>

    @GET("/method/photos.getProfile")
    suspend fun getProfilePhoto(): Response<ProfilePhotoDTO>

    @POST("/method/account.saveProfileInfo")
    suspend fun postProfileInfoStatus(
        @Query("status") status: String,
    ): Response<SaveProfileInfoDTO>

    @POST("/method/account.saveProfileInfo")
    suspend fun postProfileInfoFirstName(
        @Query("first_name") firstName: String,
    ): Response<SaveProfileInfoDTO>

    @POST("/method/account.saveProfileInfo")
    suspend fun postProfileInfoLastName(
        @Query("last_name") lastName: String,
    ): Response<SaveProfileInfoDTO>
}