package com.example.vk_api.di.module.singletones

//import com.example.data.network.RetrofitService
//import dagger.Module
//import dagger.Provides
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//private const val BASE_URL = "https://api.github.com"
//
//@Module
//class RetrofitModule {
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideApi(retrofit: Retrofit): RetrofitService {
//        return retrofit.create(RetrofitService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
//        return OkHttpClient.Builder().addInterceptor(interceptor).build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideOkHttpInterceptor(): HttpLoggingInterceptor {
//        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
//    }
//}