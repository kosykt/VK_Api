package com.example.vk_api.di.module.scopes

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.domain.*
import com.example.vk_api.di.annotation.HomeScope
import com.example.vk_api.di.annotation.ViewModelKey
import com.example.vk_api.ui.homefragment.HomeSubcomponentProvider
import com.example.vk_api.ui.homefragment.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface HomeModule {

    @HomeScope
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(vm: HomeViewModel): ViewModel

    companion object {

        @HomeScope
        @Provides
        fun provideGetProfileInfoUseCase(
            dataSourceRepository: ProfileDataSourceRepository,
        ): GetProfileInfoUseCase {
            return GetProfileInfoUseCase(dataSourceRepository)
        }

        @HomeScope
        @Provides
        fun provideGetProfilePhotoUseCase(
            profileDataSourceRepository: ProfileDataSourceRepository,
        ): GetProfilePhotoUseCase {
            return GetProfilePhotoUseCase(profileDataSourceRepository)
        }

        @HomeScope
        @Provides
        fun providePostProfileInfoStatusUseCase(
            profileDataSourceRepository: ProfileDataSourceRepository,
        ): PostProfileInfoStatusUseCase {
            return PostProfileInfoStatusUseCase(profileDataSourceRepository)
        }

        @HomeScope
        @Provides
        fun providePostProfileInfoLastNameUseCase(
            profileDataSourceRepository: ProfileDataSourceRepository,
        ): PostProfileInfoLastNameUseCase {
            return PostProfileInfoLastNameUseCase(profileDataSourceRepository)
        }

        @HomeScope
        @Provides
        fun providePostProfileInfoFirstNameUseCase(
            profileDataSourceRepository: ProfileDataSourceRepository,
        ): PostProfileInfoFirstNameUseCase {
            return PostProfileInfoFirstNameUseCase(profileDataSourceRepository)
        }

        @HomeScope
        @Provides
        fun provideHomeSubcomponentProvider(
            application: Application,
        ): HomeSubcomponentProvider {
            return (application as HomeSubcomponentProvider)
        }
    }
}