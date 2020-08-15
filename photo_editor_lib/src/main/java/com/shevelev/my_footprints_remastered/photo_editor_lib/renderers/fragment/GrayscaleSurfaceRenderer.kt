package com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.fragment

import android.content.Context
import android.graphics.Bitmap
import com.shevelev.my_footprints_remastered.photo_editor_lib.R
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.GLSurfaceShaderRenderedBase

class GrayscaleSurfaceRenderer(context: Context, bitmap: Bitmap): GLSurfaceShaderRenderedBase(context, bitmap, R.raw.gray)