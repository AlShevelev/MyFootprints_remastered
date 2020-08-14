package com.shevelev.photo_editor.open_gl.renderers.effect

import android.content.Context
import android.graphics.Bitmap
import android.media.effect.Effect
import android.media.effect.EffectFactory
import com.shevelev.photo_editor.open_gl.renderers.GLSurfaceEffectRenderedBase
import com.shevelev.photo_editor.open_gl.renderers.effect.effects.EffectBase

class MultiEffectSurfaceRenderer(
    context: Context,
    bitmap: Bitmap,
    private val effects: List<EffectBase>,
    private var effectIndex: Int
): GLSurfaceEffectRenderedBase(context, bitmap) {

    val sourceFactor: Float
        get() = effects[effectIndex].sourceFactor

    override fun createEffect(factory: EffectFactory): Effect = effects[effectIndex].createEffect(factory)

    fun update(factor: Float) {
        effects[effectIndex].sourceFactor = factor
        surface.requestRender()
    }

    fun switch(index: Int) {
        effectIndex = index
        surface.requestRender()
    }
}
