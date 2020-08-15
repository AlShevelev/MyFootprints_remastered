package com.shevelev.photo_editor

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.shevelev.my_footprints_remastered.photo_editor_lib.GLSurfaceViewBitmap
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects.BrightnessEffect
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects.ContrastEffect
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects.SaturationEffect
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects.TemperatureEffect
import com.shevelev.photo_editor.cross_activity_communication.CrossActivityCommunicator
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.MultiEffectSurfaceRenderer
import kotlinx.android.synthetic.main.activity_combined.*

class CombinedActivity : AppCompatActivity() {
    companion object {
        const val REQUEST = 1056

        private const val BRIGHTNESS = 0
        private const val CONTRAST = 1
        private const val SATURATION = 2
        private const val TEMPERATURE = 3
    }

    private lateinit var buttons: List<Button>

    private lateinit var renderer: MultiEffectSurfaceRenderer

    private var currentEffectIndex = BRIGHTNESS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combined)

        val bitmap = CrossActivityCommunicator.bitmap!!
        renderer = MultiEffectSurfaceRenderer(
            this,
            bitmap,
            listOf(
                BrightnessEffect(filterValuesBar.progress.toFloat()),
                ContrastEffect(filterValuesBar.progress.toFloat()),
                SaturationEffect(filterValuesBar.progress.toFloat()),
                TemperatureEffect(filterValuesBar.progress.toFloat())
            ),
            BRIGHTNESS)
        val surface = GLSurfaceViewBitmap.createAndAddToView(this, surfaceContainer, bitmap, renderer)

        brightnessButton.isSelected = true

        buttons = listOf(brightnessButton, contrastButton, saturationButton, temperatureButton)

        brightnessButton.setOnClickListener { onButtonClick(BRIGHTNESS) }
        contrastButton.setOnClickListener { onButtonClick(CONTRAST) }
        saturationButton.setOnClickListener { onButtonClick(SATURATION) }
        temperatureButton.setOnClickListener { onButtonClick(TEMPERATURE) }

        filterValuesBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                renderer.update(progress.toFloat())
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

    private fun onButtonClick(clickedIndex: Int) {
        if(clickedIndex == currentEffectIndex) {
            return
        }

        buttons.forEachIndexed { index, button ->
            button.isSelected = index == clickedIndex
        }
        renderer.switch(clickedIndex)
        filterValuesBar.progress = renderer.sourceFactor.toInt()
        currentEffectIndex = clickedIndex
    }
}