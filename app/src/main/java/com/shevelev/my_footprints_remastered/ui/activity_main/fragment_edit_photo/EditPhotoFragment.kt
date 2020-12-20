package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.photo_editor_lib.GLSurfaceViewBitmap
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects.BrightnessEffect
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects.ContrastEffect
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects.SaturationEffect
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.effects.TemperatureEffect
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_edit_photo.di.EditPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase
import com.shevelev.my_footprints_remastered.ui.shared.standard_widet_ext.setOnChangeListener
import com.shevelev.my_footprints_remastered.photo_editor_lib.renderers.effect.MultiEffectSurfaceRenderer
import kotlinx.android.synthetic.main.fragment_edit_photo.*
import java.io.File
import javax.inject.Inject

class EditPhotoFragment : FragmentBase() {
    companion object {
        const val ARG_FILE = "ARG_FILE"

        private const val CURRENT_FILTER_INDEX_KEY = "CURRENT_FILTER_INDEX_KEY"
        private const val BRIGHTNESS_FILTER_PROGRESS_KEY = "BRIGHTNESS_FILTER_INDEX_KEY"
        private const val CONTRAST_FILTER_PROGRESS_KEY = "CONTRAST_FILTER_INDEX_KEY"
        private const val SATURATION_FILTER_PROGRESS_KEY = "SATURATION_FILTER_INDEX_KEY"
        private const val TEMPERATURE_FILTER_PROGRESS_KEY = "TEMPERATURE_FILTER_INDEX_KEY"

        private const val BRIGHTNESS = 0
        private const val CONTRAST = 1
        private const val SATURATION = 2
        private const val TEMPERATURE = 3
    }

    private var savingInProgress = false

    override val isBackHandlerEnabled: Boolean = true

    private lateinit var buttons: List<Button>

    private lateinit var renderer: MultiEffectSurfaceRenderer

    private lateinit var surface: GLSurfaceViewBitmap

    @Inject
    internal lateinit var navigation: MainActivityNavigation

    private var currentFilterIndex = -1

    @Inject
    internal lateinit var dataBridge: CreateFootprintFragmentDataBridge

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val defaultProgress = effectValuesBar.progress

        currentFilterIndex = savedInstanceState?.getInt(CURRENT_FILTER_INDEX_KEY, BRIGHTNESS) ?: BRIGHTNESS
        val brightnessProgress = savedInstanceState?.getInt(BRIGHTNESS_FILTER_PROGRESS_KEY, defaultProgress) ?: defaultProgress
        val contrastProgress = savedInstanceState?.getInt(CONTRAST_FILTER_PROGRESS_KEY, defaultProgress) ?: defaultProgress
        val saturationProgress = savedInstanceState?.getInt(SATURATION_FILTER_PROGRESS_KEY, defaultProgress) ?: defaultProgress
        val temperatureProgress = savedInstanceState?.getInt(TEMPERATURE_FILTER_PROGRESS_KEY, defaultProgress) ?: defaultProgress

        val file = File(requireArguments().getString(ARG_FILE)!!)
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)

        renderer = MultiEffectSurfaceRenderer(
            requireContext(),
            bitmap,
            listOf(
                BrightnessEffect(brightnessProgress.toFloat()),
                ContrastEffect(contrastProgress.toFloat()),
                SaturationEffect(saturationProgress.toFloat()),
                TemperatureEffect(temperatureProgress.toFloat())
            ),
            BRIGHTNESS)
        surface = GLSurfaceViewBitmap.createAndAddToView(requireContext(), surfaceContainer, bitmap, renderer)

        buttons = listOf(brightnessButton, contrastButton, saturationButton, temperatureButton)

        brightnessButton.setOnClickListener { onButtonClick(BRIGHTNESS) }
        contrastButton.setOnClickListener { onButtonClick(CONTRAST) }
        saturationButton.setOnClickListener { onButtonClick(SATURATION) }
        temperatureButton.setOnClickListener { onButtonClick(TEMPERATURE) }

        effectValuesBar.setOnChangeListener { renderer.update(it.toFloat()) }

        cancelButton.setOnClickListener { complete() }

        acceptButton.setOnClickListener {
            if(!savingInProgress) {
                savingInProgress = true
                surface.getBitmap {
                    dataBridge.putPhoto(it!!)
                    savingInProgress = false
                    complete()
                }
            }
        }

        buttons[currentFilterIndex].performClick()
    }

    override fun inject(key: String) = App.injections.get<EditPhotoFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<EditPhotoFragmentComponent>(key)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(CURRENT_FILTER_INDEX_KEY, currentFilterIndex)
        outState.putInt(BRIGHTNESS_FILTER_PROGRESS_KEY, renderer.getSourceFactor(BRIGHTNESS).toInt())
        outState.putInt(CONTRAST_FILTER_PROGRESS_KEY, renderer.getSourceFactor(CONTRAST).toInt())
        outState.putInt(SATURATION_FILTER_PROGRESS_KEY, renderer.getSourceFactor(SATURATION).toInt())
        outState.putInt(TEMPERATURE_FILTER_PROGRESS_KEY, renderer.getSourceFactor(TEMPERATURE).toInt())
    }

    override fun onBackPressed() {
        complete()
    }

    private fun complete() {
        if(!savingInProgress) {
            navigation.moveBack(this)
        }
    }

    private fun onButtonClick(clickedIndex: Int) {
        buttons.forEachIndexed { index, button ->
            button.isSelected = index == clickedIndex
        }

        surface.getBitmap { bitmap ->
            renderer = renderer.clone(requireContext(), bitmap!!, clickedIndex)
            surface = GLSurfaceViewBitmap.createAndAddToView(requireContext(), surfaceContainer, bitmap, renderer)

            effectValuesBar.progress = renderer.sourceFactor.toInt()
            currentFilterIndex = clickedIndex
        }
    }
}