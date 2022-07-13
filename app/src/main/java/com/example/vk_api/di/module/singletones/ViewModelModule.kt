package com.example.vk_api.di.module.singletones

import androidx.lifecycle.ViewModelProvider
import com.example.vk_api.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}