package com.example.vk_api.di.module.singletones

import com.example.data.network.RetrofitService
import com.example.vk_api.utils.auth.AuthRepository
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.vk.com"

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptorL: HttpLoggingInterceptor,
        interceptorT: TokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptorT)
            .addInterceptor(interceptorL)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(
        authRepository: AuthRepository
    ): TokenInterceptor {
        return TokenInterceptor(authRepository)
    }

    class TokenInterceptor(
        private val authRepository: AuthRepository
    ) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            val request = chain.request()
            val token = authRepository.token
            val newUrl = request.url.newBuilder()
                .addQueryParameter(QUERY_ACCESS_TOKEN, token)
                .addQueryParameter(QUERY_VERSION_VK_API, VERSION_VK_API)
                .build()
            val newRequest = request.newBuilder().url(newUrl).build()
            return chain.proceed(newRequest)
        }
    }

    companion object {
        private const val QUERY_ACCESS_TOKEN = "access_token"
        private const val QUERY_VERSION_VK_API = "v"
        private const val VERSION_VK_API = "5.131"
    }
}