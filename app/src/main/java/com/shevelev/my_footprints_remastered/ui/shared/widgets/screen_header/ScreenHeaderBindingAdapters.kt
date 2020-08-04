package com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header

import androidx.databinding.BindingAdapter
import com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header.ScreenHeader

@BindingAdapter("title")
fun bindScreenHeaderTitle(view: ScreenHeader, valueToBind: String?) {
    valueToBind?.let { view.setTitle(it) }
}

@BindingAdapter("on_back_click")
fun bindBackButtonClick(view: ScreenHeader, callbackAction: () -> Unit) {
    view.setOnBackButtonClickListener {
        callbackAction()
    }
}
