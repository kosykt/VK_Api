package com.example.data.network

import com.example.data.network.profileinfomodel.ProfileInfoDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/method/account.getProfileInfo")
    suspend fun getProfileInfo(
        @Query("access_token") token: String,
        @Query("v") v: String = "5.131",
    ): Response<ProfileInfoDTO>
}