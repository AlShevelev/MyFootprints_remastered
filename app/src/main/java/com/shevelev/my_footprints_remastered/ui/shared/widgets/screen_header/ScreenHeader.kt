package com.shevelev.my_footprints_remastered.ui.shared.widgets.screen_header

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.shevelev.my_footprints_remastered.R
import kotlinx.android.synthetic.main.view_screen_header.view.*

class ScreenHeader
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var onBackClickListener: (() -> Unit)? = null

    init {
        inflate(context, R.layout.view_screen_header, this)

        backButton.setOnClickListener { onBackClickListener?.invoke() }
    }

    fun setTitle(value: String) {
        title.text = value
    }

    fun setOnBackButtonClickListener(listener: (() -> Unit)?) {
        onBackClickListener = listener
    }
}