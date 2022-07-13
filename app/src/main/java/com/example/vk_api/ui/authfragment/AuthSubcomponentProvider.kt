package com.example.vk_api.ui.authfragment

import com.example.vk_api.di.component.AuthSubcomponent

interface AuthSubcomponentProvider {

    fun initAuthSubcomponent(): AuthSubcomponent
    fun destroyAuthSubcomponent()
}