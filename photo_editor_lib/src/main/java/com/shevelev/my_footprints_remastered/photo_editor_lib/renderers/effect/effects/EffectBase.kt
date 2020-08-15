package com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects

import android.media.effect.Effect
import android.media.effect.EffectFactory
import android.util.Range
import com.shevelev.my_footprints_remastered.utils.ranges.reduceToRange

abstract class EffectBase(
    private val sourceFactorRange: Range<Float>,
    sourceFactorStartValue: Float,
    private val effectFactorRange: Range<Float>,
    protected var effectFactor: Float
) {
    var sourceFactor: Float = sourceFactorStartValue
        set(value) {
            field = value
            this.effectFactor = value.reduceToRange(sourceFactorRange, effectFactorRange)
        }


    abstract fun createEffect(factory: EffectFactory): Effect
}