package com.example.vk_api.utils.imageloader

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.vk_api.R

class CoilImageLoader : AppImageLoader {

    override fun loadInto(url: String, container: ImageView) {
        container.load(url) {
            target(
                onSuccess = { result ->
                    container.setImageDrawable(result)
                },
                onStart = {
                    container.setImageResource(R.drawable.ar_loading_animation)
                },
                onError = {
                    container.setImageResource(R.drawable.ic_baseline_error_outline_24)
                }
            )
            transformations(
                CircleCropTransformation(),
            )
        }
    }
}