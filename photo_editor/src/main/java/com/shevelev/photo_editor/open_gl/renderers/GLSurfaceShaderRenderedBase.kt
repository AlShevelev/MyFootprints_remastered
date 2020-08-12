package com.shevelev.photo_editor.open_gl.renderers

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.RawRes
import javax.microedition.khronos.opengles.GL10

/**
 * Base class for all OpenGL renderers, based on fragment shaders
 */
abstract class GLSurfaceShaderRenderedBase(
    private val context: Context,
    private val bitmap: Bitmap,
    @RawRes
    private val fragmentShaderResId: Int
) : GLSurfaceRenderedBase(context, bitmap, fragmentShaderResId) {

    override fun onDrawFrame(gl: GL10) {
        draw(textures[0])
        tryToGetFrameAsBitmap(gl)
    }
}