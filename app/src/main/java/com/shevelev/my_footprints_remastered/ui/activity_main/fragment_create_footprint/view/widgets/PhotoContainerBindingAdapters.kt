package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets

import androidx.databinding.BindingAdapter

interface PhotoContainerAddPhotoCall {
    fun onAddPhotoClick()
}

interface PhotoContainerClearPhotoCall {
    fun onClearPhotoClick()
}

interface PhotoContainerEditPhotoCall {
    fun onEditPhotoClick()
}

interface PhotoContainerBindingCall: PhotoContainerAddPhotoCall, PhotoContainerClearPhotoCall, PhotoContainerEditPhotoCall

@BindingAdapter("on_add_click")
fun bindAddButtonClick(view: PhotoContainer, callback: PhotoContainerAddPhotoCall) {
    view.setOnAddClickListener {
        callback.onAddPhotoClick()
    }
}

@BindingAdapter("on_clear_click")
fun bindClearButtonClick(view: PhotoContainer, callback: PhotoContainerClearPhotoCall) {
    view.setOnClearClickListener {
        callback.onClearPhotoClick()
    }
}

@BindingAdapter("on_edit_click")
fun bindEditButtonClick(view: PhotoContainer, callback: PhotoContainerEditPhotoCall) {
    view.setOnEditClickListener {
        callback.onEditPhotoClick()
    }
}

@BindingAdapter("state")
fun bindSetState(view: PhotoContainer, state: PhotoContainerState) {
    view.state = state
}