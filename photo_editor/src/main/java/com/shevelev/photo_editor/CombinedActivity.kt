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
import com.shevelev.photo_editor.databinding.ActivityCombinedBinding

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

    private lateinit var binding: ActivityCombinedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCombinedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bitmap = CrossActivityCommunicator.bitmap!!
        renderer = MultiEffectSurfaceRenderer(
            this,
            bitmap,
            listOf(
                BrightnessEffect(binding.filterValuesBar.progress.toFloat()),
                ContrastEffect(binding.filterValuesBar.progress.toFloat()),
                SaturationEffect(binding.filterValuesBar.progress.toFloat()),
                TemperatureEffect(binding.filterValuesBar.progress.toFloat())
            ),
            BRIGHTNESS)
        val surface = GLSurfaceViewBitmap.createAndAddToView(this, binding.surfaceContainer, bitmap, renderer)

        binding.brightnessButton.isSelected = true

        buttons = listOf(binding.brightnessButton, binding.contrastButton, binding.saturationButton, binding.temperatureButton)

        binding.brightnessButton.setOnClickListener { onButtonClick(BRIGHTNESS) }
        binding.contrastButton.setOnClickListener { onButtonClick(CONTRAST) }
        binding.saturationButton.setOnClickListener { onButtonClick(SATURATION) }
        binding.temperatureButton.setOnClickListener { onButtonClick(TEMPERATURE) }

        binding.filterValuesBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

    private fun onButtonClick(clickedIndex: Int) {
        if(clickedIndex == currentEffectIndex) {
            return
        }

        buttons.forEachIndexed { index, button ->
            button.isSelected = index == clickedIndex
        }
        renderer.switch(clickedIndex)
        binding.filterValuesBar.progress = renderer.sourceFactor.toInt()
        currentEffectIndex = clickedIndex
    }
}