package com.shevelev.photo_editor.open_gl.renderers.effect

import android.content.Context
import android.graphics.Bitmap
import android.media.effect.Effect
import android.media.effect.EffectFactory
import android.util.Range
import com.shevelev.my_footprints_remastered.utils.ranges.reduceToRange
import com.shevelev.photo_editor.open_gl.renderers.GLSurfaceEffectRenderedBase

class SaturationSurfaceRenderer(context: Context, bitmap: Bitmap): GLSurfaceEffectRenderedBase(context, bitmap) {
    private var saturation = 0f     // Neutral value

    private val sourceFactorRange = Range<Float>(0f, 100f)
    private val saturationFactorRange = Range<Float>(-1f, 1f)

    override fun createEffect(factory: EffectFactory): Effect =
        factory.createEffect(EffectFactory.EFFECT_SATURATE).apply {
            setParameter("scale", saturation)
        }

    /**
     * [saturationFactor] from 0 to 100
     */
    fun updateSaturation(saturationFactor: Float) {
        this.saturation = saturationFactor.reduceToRange(sourceFactorRange, saturationFactorRange)
        surface.requestRender()
    }
}