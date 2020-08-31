package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view

import android.widget.ImageButton
import androidx.databinding.BindingAdapter

interface ButtonsBindingCall {
    fun onMoveToMapClick()
}

@BindingAdapter("on_move_to_map_click")
fun bindMoveToMapClick(view: ImageButton, callback: ButtonsBindingCall) {
    view.setOnClickListener {
        callback.onMoveToMapClick()
    }
}
