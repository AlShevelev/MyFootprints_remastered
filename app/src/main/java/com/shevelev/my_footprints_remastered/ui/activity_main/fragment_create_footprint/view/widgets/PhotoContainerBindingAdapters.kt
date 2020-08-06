package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets

import androidx.databinding.BindingAdapter

interface PhotoContainerBindingCall {
    fun onAddPhotoClick()
}

@BindingAdapter("on_add_click")
fun bindAddButtonClick(view: PhotoContainer, callback: PhotoContainerBindingCall) {
    view.setOnAddClickListener {
        callback.onAddPhotoClick()
    }
}

@BindingAdapter("state")
fun bindSetState(view: PhotoContainer, state: PhotoContainerState) {
    view.state = state
}