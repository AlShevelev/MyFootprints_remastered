package com.shevelev.my_footprints_remastered.ui.shared.glide

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import java.io.File

typealias GlideTarget = Target<*>

fun ImageView.load(image: Uri, crossFade: Boolean = false, skipMemoryCache: Boolean = false): GlideTarget =
    Glide
        .with(this)
        .load(image)
        .apply {
            if(crossFade) {
                transition(withCrossFade(createCrossFadeFactory()))
            }
        }
        .skipMemoryCache(skipMemoryCache)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)

fun ImageView.load(image: File, crossFade: Boolean = false, skipMemoryCache: Boolean = false): GlideTarget =
    Glide
        .with(this)
        .load(image)
        .apply {
            if(crossFade) {
                transition(withCrossFade(createCrossFadeFactory()))
            }
        }
        .skipMemoryCache(skipMemoryCache)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)

fun ImageView.load(@DrawableRes imageResId: Int, crossFade: Boolean = false, skipMemoryCache: Boolean = false): GlideTarget =
    Glide
        .with(this)
        .load(imageResId)
        .apply {
            if(crossFade) {
                transition(withCrossFade(createCrossFadeFactory()))
            }
        }
        .skipMemoryCache(skipMemoryCache)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)

fun GlideTarget.clear(context: Context) {
    if(context is Activity && context.isDestroyed){
        return
    }

    Glide.with(context).clear(this)
}

private fun createCrossFadeFactory() = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()