package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.shevelev.my_footprints_remastered.R
import kotlinx.android.synthetic.main.view_add_photo_stub.view.*

class AddPhotoStub
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var onAddClickListener: (() -> Unit)? = null

    init {
        inflate(context, R.layout.view_add_photo_stub, this)

        addPhotoButton.setOnClickListener { onAddClickListener?.invoke() }
    }

    fun setOnAddClickListener(listener: (() -> Unit)?) {
        onAddClickListener = listener
    }
}