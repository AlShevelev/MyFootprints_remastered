package com.shevelev.photo_editor

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shevelev.photo_editor.open_gl.GLSurfaceViewBitmap
import com.shevelev.photo_editor.open_gl.renderers.effect.BrightnessSurfaceRenderer
import com.shevelev.photo_editor.open_gl.renderers.fragment.NewspaperSurfaceRenderer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_test_port)
        val renderer = NewspaperSurfaceRenderer(this, bitmap, grayscaleCheckBox.isChecked)
//        val renderer = BrightnessSurfaceRenderer(this, bitmap)
        val surface = GLSurfaceViewBitmap.createAndAddToView(this, surfaceContainer, bitmap, renderer)

        grayscaleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            renderer.updateGrayscale(isChecked)
//            renderer.updateBrightness(1f)
        }

        getBitmapButton.setOnClickListener {
            surface.getBitmap {
                Log.d("GET_BITMAP", "[${it?.width};${it?.height}]")
            }
        }
    }
}
