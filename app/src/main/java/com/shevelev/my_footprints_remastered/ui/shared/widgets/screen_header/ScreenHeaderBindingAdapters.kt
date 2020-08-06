package com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header

import androidx.databinding.BindingAdapter

interface ScreenHeaderBindingCall {
    fun onBackClick()
}

@BindingAdapter("title")
fun bindScreenHeaderTitle(view: ScreenHeader, valueToBind: String?) {
    valueToBind?.let { view.setTitle(it) }
}

@BindingAdapter("on_back_click")
fun bindBackButtonClick(view: ScreenHeader, callback: ScreenHeaderBindingCall) {
    view.setOnBackButtonClickListener {
        callback.onBackClick()
    }
}
