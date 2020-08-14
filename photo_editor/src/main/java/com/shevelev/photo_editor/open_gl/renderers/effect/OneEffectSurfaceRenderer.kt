package com.shevelev.photo_editor.open_gl.renderers.effect

import android.content.Context
import android.graphics.Bitmap
import android.media.effect.Effect
import android.media.effect.EffectFactory
import com.shevelev.photo_editor.open_gl.renderers.GLSurfaceEffectRenderedBase
import com.shevelev.photo_editor.open_gl.renderers.effect.effects.EffectBase

class OneEffectSurfaceRenderer(
    context: Context,
    bitmap: Bitmap,
    private val effect: EffectBase
): GLSurfaceEffectRenderedBase(context, bitmap) {

    val sourceFactor: Float
        get() = effect.sourceFactor

    override fun createEffect(factory: EffectFactory): Effect = effect.createEffect(factory)

    fun update(factor: Float) {
        effect.sourceFactor = factor
        surface.requestRender()
    }
}
