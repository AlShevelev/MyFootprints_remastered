package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets

import androidx.databinding.BindingAdapter

@BindingAdapter("on_add_click")
fun bindAddButtonClick(view: AddPhotoStub, callbackAction: () -> Unit) {
    view.setOnAddClickListener {
        callbackAction()
    }
}