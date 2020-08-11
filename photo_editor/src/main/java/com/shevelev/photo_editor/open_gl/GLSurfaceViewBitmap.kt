package com.shevelev.photo_editor.open_gl

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLSurfaceView
import android.os.Handler
import android.util.AttributeSet
import android.util.SizeF
import android.view.Gravity
import android.widget.FrameLayout
import com.shevelev.photo_editor.open_gl.renderers.SurfaceRenderedBase

class GLSurfaceViewBitmap
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {

    private lateinit var renderer: SurfaceRenderedBase

    companion object {
        fun createAndAddToView(context: Context, root: FrameLayout, bitmap: Bitmap, renderer: SurfaceRenderedBase): GLSurfaceViewBitmap {
            val surfaceView = GLSurfaceViewBitmap(context)

            surfaceView.setBitmapParameters(bitmap)

            root .addView(surfaceView, 0)

            val layoutParams = surfaceView.layoutParams as FrameLayout.LayoutParams
            layoutParams.gravity = Gravity.CENTER
            surfaceView.layoutParams = layoutParams

            surfaceView.setEGLContextClientVersion(2)
            surfaceView.setRenderer(renderer)
            surfaceView.renderMode = RENDERMODE_WHEN_DIRTY

            surfaceView.renderer = renderer

            return surfaceView
        }
    }

    private lateinit var bitmapSize: SizeF

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val areaWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        val areaHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()

        var testSize = (areaHeight / bitmapSize.height) * bitmapSize.width

        if(testSize <= areaWidth) {
            setMeasuredDimension(testSize.toInt(), areaHeight.toInt())
        } else {
            testSize = (areaWidth / bitmapSize.width) * bitmapSize.height
            setMeasuredDimension(areaWidth.toInt(), testSize.toInt())
        }
    }

    fun getBitmap(callback: (Bitmap?) -> Unit) {
        renderer.startGetFrameAsBitmap(Handler(), callback)
        requestRender()
    }

    private fun setBitmapParameters(bitmap: Bitmap) {
        bitmapSize = SizeF(bitmap.width.toFloat(), bitmap.height.toFloat())
    }
}