package com.example.vk_api.utils.imageloader

import android.widget.ImageView

interface AppImageLoader {

    fun loadInto(url: String, container: ImageView)
}