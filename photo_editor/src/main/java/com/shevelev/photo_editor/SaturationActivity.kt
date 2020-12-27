package com.shevelev.photo_editor

import android.app.Activity
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.shevelev.my_footprints_remastered.photo_editor_lib.GLSurfaceViewBitmap
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects.SaturationEffect
import com.shevelev.photo_editor.cross_activity_communication.CrossActivityCommunicator
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.OneEffectSurfaceRenderer
import com.shevelev.photo_editor.databinding.ActivitySaturationBinding

class SaturationActivity : AppCompatActivity() {
    companion object {
        const val REQUEST = 101
    }

    private lateinit var binding: ActivitySaturationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySaturationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bitmap = CrossActivityCommunicator.bitmap!!
        val renderer = OneEffectSurfaceRenderer(this, bitmap, SaturationEffect(binding.saturationBar.progress.toFloat()))
        val surface = GLSurfaceViewBitmap.createAndAddToView(this, binding.surfaceContainer, bitmap, renderer)

        binding.saturationBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                renderer.update(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        binding.acceptButton.setOnClickListener {
            surface.getBitmap {
                CrossActivityCommunicator.bitmap = it
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}