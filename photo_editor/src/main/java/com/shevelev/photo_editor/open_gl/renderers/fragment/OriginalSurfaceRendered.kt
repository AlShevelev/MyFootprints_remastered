package com.shevelev.photo_editor.open_gl.renderers.fragment

import android.content.Context
import android.graphics.Bitmap
import com.shevelev.photo_editor.R
import com.shevelev.photo_editor.open_gl.renderers.GLSurfaceShaderRenderedBase

class OriginalSurfaceRendered(context: Context, bitmap: Bitmap): GLSurfaceShaderRenderedBase(context, bitmap, R.raw.original)