package com.example.vk_api.di.module.singletones

import android.app.Application
import com.example.data.network.NetworkDataSourceImpl
import com.example.data.network.RetrofitService
import com.example.data.repository.DataSourceRepositoryImpl
import com.example.data.repository.NetworkDataSource
import com.example.domain.DataSourceRepository
import com.example.vk_api.utils.NetworkObserver
import com.example.vk_api.utils.auth.AuthRepository
import com.example.vk_api.utils.auth.AuthRepositoryImpl
import com.example.vk_api.utils.imageloader.AppImageLoader
import com.example.vk_api.utils.imageloader.CoilImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val VK_ACCOUNT = "vk_account"

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideNetworkObserver(): NetworkObserver {
        return NetworkObserver(application)
    }

    @Singleton
    @Provides
    fun provideAppImageLoader(): AppImageLoader {
        return CoilImageLoader()
    }

    @Singleton
    @Provides
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(application.getSharedPreferences(VK_ACCOUNT,
            Application.MODE_PRIVATE))
    }

    @Singleton
    @Provides
    fun provideDataSourceRepository(
        network: NetworkDataSource,
    ): DataSourceRepository {
        return DataSourceRepositoryImpl(network)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(retrofitService: RetrofitService): NetworkDataSource {
        return NetworkDataSourceImpl(retrofitService)
    }
}