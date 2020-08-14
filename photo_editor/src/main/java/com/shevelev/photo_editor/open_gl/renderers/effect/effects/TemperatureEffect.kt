package com.shevelev.photo_editor.open_gl.renderers.effect.effects

import android.media.effect.EffectFactory
import android.util.Range

class TemperatureEffect(
    sourceFactorStartValue: Float
) : EffectBase(
    Range<Float>(0f, 100f),
    sourceFactorStartValue,
    Range<Float>(0f, 1f),
    0.5f) {

    override fun createEffect(factory: EffectFactory): android.media.effect.Effect =
        factory.createEffect(EffectFactory.EFFECT_TEMPERATURE).apply {
            setParameter("scale", effectFactor)
        }
}