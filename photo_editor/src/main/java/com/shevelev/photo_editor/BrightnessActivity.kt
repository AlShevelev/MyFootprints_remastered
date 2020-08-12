package com.shevelev.photo_editor

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.SeekBar
import com.shevelev.photo_editor.cross_activity_communication.CrossActivityCommunicator
import com.shevelev.photo_editor.open_gl.GLSurfaceViewBitmap
import com.shevelev.photo_editor.open_gl.renderers.effect.BrightnessSurfaceRenderer
import kotlinx.android.synthetic.main.activity_brightness.*

class BrightnessActivity : AppCompatActivity() {
    companion object {
        const val REQUEST = 42
        const val BITMAP = "BITMAP_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brightness)

        val bitmap = CrossActivityCommunicator.bitmap!!
        val renderer = BrightnessSurfaceRenderer(this, bitmap)
        val surface = GLSurfaceViewBitmap.createAndAddToView(this, surfaceContainer, bitmap, renderer)

        brightnessBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                renderer.updateBrightness(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        acceptButton.setOnClickListener {
            surface.getBitmap {
                CrossActivityCommunicator.bitmap = it
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}