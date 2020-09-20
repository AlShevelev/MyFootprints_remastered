package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view

import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.BindingAdapter

interface MoveToMapBindingCall {
    fun onMoveToMapClick()
}

interface SaveBindingCall {
    fun onSaveClick()
}

interface ButtonsBindingCall: MoveToMapBindingCall, SaveBindingCall

@BindingAdapter("on_move_to_map_click")
fun bindMoveToMapClick(view: ImageButton, callback: MoveToMapBindingCall) {
    view.setOnClickListener {
        callback.onMoveToMapClick()
    }
}

@BindingAdapter("on_save_click")
fun bindSaveClick(view: Button, callback: SaveBindingCall) {
    view.setOnClickListener {
        callback.onSaveClick()
    }
}

