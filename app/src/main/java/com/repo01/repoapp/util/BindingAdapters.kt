package com.repo01.repoapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        url?.let {
            Glide.with(view.context)
                .load(url)
                .into(view)
        }
    }
}