package com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects

import android.media.effect.EffectFactory
import android.util.Range

class ContrastEffect(
    sourceFactorStartValue: Float
) : EffectBase(
    Range<Float>(0f, 100f),
    sourceFactorStartValue,
    Range<Float>(0.5f, 1.5f),
    1f) {

    override fun createEffect(factory: EffectFactory): android.media.effect.Effect =
        factory.createEffect(EffectFactory.EFFECT_CONTRAST).apply {
            setParameter("contrast", effectFactor)
        }
}