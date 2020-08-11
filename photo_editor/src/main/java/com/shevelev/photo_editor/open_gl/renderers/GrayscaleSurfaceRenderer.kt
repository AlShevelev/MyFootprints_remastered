package com.shevelev.photo_editor.open_gl.renderers

import android.content.Context
import android.graphics.Bitmap
import com.shevelev.photo_editor.R

class GrayscaleSurfaceRenderer(context: Context, bitmap: Bitmap): SurfaceRenderedBase(context, bitmap, R.raw.gray)