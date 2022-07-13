package com.example.vk_api.di.component

import com.example.vk_api.di.annotation.AuthScope
import com.example.vk_api.ui.authfragment.AuthFragment
import dagger.Subcomponent

@AuthScope
@Subcomponent
interface AuthSubcomponent {

    fun inject(fragment: AuthFragment)
}