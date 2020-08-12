package com.shevelev.photo_editor.open_gl.renderers.effect

import android.content.Context
import android.graphics.Bitmap
import android.media.effect.Effect
import android.media.effect.EffectFactory
import android.util.Range
import com.shevelev.my_footprints_remastered.utils.ranges.reduceToRange
import com.shevelev.photo_editor.open_gl.renderers.GLSurfaceEffectRenderedBase

class ContrastSurfaceRenderer(context: Context, bitmap: Bitmap): GLSurfaceEffectRenderedBase(context, bitmap) {
    private var contrast = 1f     // Neutral value

    private val sourceFactorRange = Range<Float>(0f, 100f)
    private val contrastFactorRange = Range<Float>(0.5f, 1.5f)

    override fun createEffect(factory: EffectFactory): Effect =
        factory.createEffect(EffectFactory.EFFECT_CONTRAST).apply {
            setParameter("contrast", contrast)
        }

    /**
     * [contrastFactor] from 0 to 100
     */
    fun updateContrast(contrastFactor: Float) {
        this.contrast = contrastFactor.reduceToRange(sourceFactorRange, contrastFactorRange)
        surface.requestRender()
    }
}
