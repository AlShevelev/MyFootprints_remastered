package com.shevelev.photo_editor.open_gl.renderers.effect

import android.content.Context
import android.graphics.Bitmap
import android.media.effect.Effect
import android.media.effect.EffectFactory
import com.shevelev.photo_editor.open_gl.renderers.GLSurfaceEffectRenderedBase

class BrightnessSurfaceRenderer(context: Context, bitmap: Bitmap): GLSurfaceEffectRenderedBase(context, bitmap) {
    private var brightness = 2f

    override fun createEffect(factory: EffectFactory): Effect =
        factory.createEffect(EffectFactory.EFFECT_BRIGHTNESS).apply {
            setParameter("brightness", brightness)
        }

    fun updateBrightness(brightness: Float) {
        this.brightness = brightness
        surface.requestRender()
    }
}
