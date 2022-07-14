package com.example.vk_api.di.component

import com.example.vk_api.di.annotation.HomeScope
import com.example.vk_api.di.module.scopes.HomeModule
import com.example.vk_api.ui.homefragment.HomeFragment
import dagger.Subcomponent

@HomeScope
@Subcomponent(modules = [HomeModule::class])
interface HomeSubcomponent {

    fun inject(fragment: HomeFragment)
}