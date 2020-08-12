package com.shevelev.photo_editor

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.shevelev.photo_editor.cross_activity_communication.CrossActivityCommunicator
import com.shevelev.photo_editor.open_gl.GLSurfaceViewBitmap
import com.shevelev.photo_editor.open_gl.renderers.effect.BrightnessSurfaceRenderer
import com.shevelev.photo_editor.open_gl.renderers.effect.WhiteBalanceSurfaceRenderer
import kotlinx.android.synthetic.main.activity_white_balance.*

class WhiteBalanceActivity : AppCompatActivity() {
    companion object {
        const val REQUEST = 118
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_white_balance)

        val bitmap = CrossActivityCommunicator.bitmap!!
        val renderer = WhiteBalanceSurfaceRenderer(this, bitmap)
        val surface = GLSurfaceViewBitmap.createAndAddToView(this, surfaceContainer, bitmap, renderer)

        whiteBalanceBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                renderer.updateWhiteBalance(progress.toFloat())
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