package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets

import androidx.databinding.BindingAdapter

interface PhotoContainerAddPhotoCall {
    fun onAddPhotoClick()
}

interface PhotoContainerClearPhotoCall {
    fun onClearPhotoClick()
}

interface PhotoContainerCropPhotoCall {
    fun onCropPhotoClick()
}

interface PhotoContainerFilterPhotoCall {
    fun onFilterPhotoClick()
}

interface PhotoContainerBindingCall:
    PhotoContainerAddPhotoCall,
    PhotoContainerClearPhotoCall,
    PhotoContainerCropPhotoCall,
    PhotoContainerFilterPhotoCall

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

@BindingAdapter("on_crop_click")
fun bindCropButtonClick(view: PhotoContainer, callback: PhotoContainerCropPhotoCall) {
    view.setOnCropClickListener {
        callback.onCropPhotoClick()
    }
}

@BindingAdapter("on_filter_click")
fun bindFilterButtonClick(view: PhotoContainer, callback: PhotoContainerFilterPhotoCall) {
    view.setOnFilterClickListener {
        callback.onFilterPhotoClick()
    }
}

@BindingAdapter("state")
fun bindSetState(view: PhotoContainer, state: PhotoContainerState) {
    view.state = state
}