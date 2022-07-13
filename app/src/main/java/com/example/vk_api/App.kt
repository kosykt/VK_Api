package com.example.vk_api

import android.app.Application
import com.example.vk_api.di.component.AppComponent
import com.example.vk_api.di.component.AuthSubcomponent
import com.example.vk_api.di.component.DaggerAppComponent
import com.example.vk_api.di.module.singletones.AppModule
import com.example.vk_api.ui.authfragment.AuthSubcomponentProvider

class App : Application(), AuthSubcomponentProvider {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private var authSubcomponent: AuthSubcomponent? = null

    override fun initAuthSubcomponent(): AuthSubcomponent = appComponent
        .provideAuthSubcomponent()
        .also {
            if (authSubcomponent == null) {
                authSubcomponent = it
            }
        }

    override fun destroyAuthSubcomponent() {
        authSubcomponent = null
    }
}