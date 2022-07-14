package com.example.vk_api.di.module.scopes

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.domain.DataSourceRepository
import com.example.domain.GetProfileInfoUseCase
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

    companion object{

        @HomeScope
        @Provides
        fun provideGetReposUseCase(
            dataSourceRepository: DataSourceRepository
        ): GetProfileInfoUseCase {
            return GetProfileInfoUseCase(dataSourceRepository)
        }

        @HomeScope
        @Provides
        fun provideHomeSubcomponentProvider(
            application: Application
        ): HomeSubcomponentProvider {
            return (application as HomeSubcomponentProvider)
        }
    }
}