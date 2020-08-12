package com.shevelev.photo_editor.open_gl.renderers.effect

import android.content.Context
import android.graphics.Bitmap
import android.media.effect.Effect
import android.media.effect.EffectFactory
import android.util.Range
import com.shevelev.my_footprints_remastered.utils.ranges.reduceToRange
import com.shevelev.photo_editor.open_gl.renderers.GLSurfaceEffectRenderedBase

class WhiteBalanceSurfaceRenderer(context: Context, bitmap: Bitmap): GLSurfaceEffectRenderedBase(context, bitmap) {
    private var whiteBalance = 0.5f     // Neutral value

    private val sourceFactorRange = Range<Float>(0f, 100f)
    private val whiteBalanceFactorRange = Range<Float>(0f, 1f)

    override fun createEffect(factory: EffectFactory): Effect =
        factory.createEffect(EffectFactory.EFFECT_TEMPERATURE).apply {
            setParameter("scale", whiteBalance)
        }

    /**
     * [whiteBalanceFactor] from 0 to 100
     */
    fun updateWhiteBalance(whiteBalanceFactor: Float) {
        this.whiteBalance = whiteBalanceFactor.reduceToRange(sourceFactorRange, whiteBalanceFactorRange)
        surface.requestRender()
    }
}