package com.shevelev.photo_editor.open_gl.renderers

import android.content.Context
import android.graphics.Bitmap
import android.media.effect.Effect
import android.media.effect.EffectContext
import android.media.effect.EffectFactory
import com.shevelev.photo_editor.R
import javax.microedition.khronos.opengles.GL10

/**
 * Base class for all OpenGL renderers, based on Android Media Effects
 */
abstract class GLSurfaceEffectRenderedBase(
    private val context: Context,
    private val bitmap: Bitmap
) : GLSurfaceRenderedBase(context, bitmap, R.raw.original) {

    private var effectContext: EffectContext? = null
    private var effect: Effect? = null

    override fun onDrawFrame(gl: GL10) {
        effectContext = effectContext ?: EffectContext.createWithCurrentGlContext()
        effect?.release()

        effect = createEffect(effectContext!!.factory)
        effect!!.apply(textures[0], surfaceSize.width, surfaceSize.height, textures[1])

        draw(textures[1])

        tryToGetFrameAsBitmap(gl)
    }

    protected abstract fun createEffect(factory: EffectFactory): Effect
}