package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_pages.view

import android.widget.ImageButton
import androidx.databinding.BindingAdapter

interface ClickToMapBindingCall {
    fun onMapClick()
}

interface ClickToShareBindingCall {
    fun onShareClick()
}

interface ClickToEditBindingCall {
    fun onEditClick()
}

interface ClickToDeleteBindingCall {
    fun onDeleteClick()
}

interface ButtonsBindingCall: ClickToMapBindingCall, ClickToShareBindingCall, ClickToEditBindingCall, ClickToDeleteBindingCall

@BindingAdapter("on_map_click")
fun bindOnMapClick(view: ImageButton, callback: ClickToMapBindingCall) {
    view.setOnClickListener {
        callback.onMapClick()
    }
}

@BindingAdapter("on_share_click")
fun bindOnShareClick(view: ImageButton, callback: ClickToShareBindingCall) {
    view.setOnClickListener {
        callback.onShareClick()
    }
}

@BindingAdapter("on_edit_click")
fun bindOnEditClick(view: ImageButton, callback: ClickToEditBindingCall) {
    view.setOnClickListener {
        callback.onEditClick()
    }
}

@BindingAdapter("on_delete_click")
fun bindOnDeleteClick(view: ImageButton, callback: ClickToDeleteBindingCall) {
    view.setOnClickListener {
        callback.onDeleteClick()
    }
}
