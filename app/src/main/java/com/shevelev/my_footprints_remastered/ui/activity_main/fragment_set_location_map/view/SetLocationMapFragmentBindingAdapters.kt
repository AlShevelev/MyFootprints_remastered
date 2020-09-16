package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_set_location_map.view

import android.widget.ImageButton
import androidx.databinding.BindingAdapter

interface ColorDialogButtonsBindingCall {
    fun onColorDialogButtonClick()
}

interface HelpButtonsBindingCall {
    fun onHelpButtonClick()
}

interface ButtonsBindingCall : ColorDialogButtonsBindingCall, HelpButtonsBindingCall

@BindingAdapter("on_color_dialog_click")
fun bindColorDialogClick(view: ImageButton, callback: ColorDialogButtonsBindingCall) {
    view.setOnClickListener {
        callback.onColorDialogButtonClick()
    }
}

@BindingAdapter("on_help_click")
fun bindHelpClick(view: ImageButton, callback: HelpButtonsBindingCall) {
    view.setOnClickListener {
        callback.onHelpButtonClick()
    }
}