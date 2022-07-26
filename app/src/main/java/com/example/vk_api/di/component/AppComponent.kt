package com.example.vk_api.di.component

import com.example.vk_api.di.module.singletones.AppModule
import com.example.vk_api.di.module.singletones.RetrofitModule
import com.example.vk_api.di.module.singletones.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        RetrofitModule::class
    ]
)
interface AppComponent {

    fun provideAuthSubcomponent(): AuthSubcomponent
    fun provideHomeSubcomponent(): HomeSubcomponent
}