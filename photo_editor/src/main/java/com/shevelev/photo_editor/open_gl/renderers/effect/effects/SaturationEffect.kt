package com.shevelev.photo_editor.open_gl.renderers.effect.effects

import android.media.effect.EffectFactory
import android.util.Range

class SaturationEffect(
    sourceFactorStartValue: Float
) : EffectBase(
    Range<Float>(0f, 100f),
    sourceFactorStartValue,
    Range<Float>(-1f, 1f),
    0f) {

    override fun createEffect(factory: EffectFactory): android.media.effect.Effect =
        factory.createEffect(EffectFactory.EFFECT_SATURATE).apply {
            setParameter("scale", effectFactor)
        }
}