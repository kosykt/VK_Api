package com.example.vk_api.ui.homefragment

import com.example.vk_api.di.component.HomeSubcomponent

interface HomeSubcomponentProvider {

    fun initHomeSubcomponent(): HomeSubcomponent
    fun destroyHomeSubcomponent()
}