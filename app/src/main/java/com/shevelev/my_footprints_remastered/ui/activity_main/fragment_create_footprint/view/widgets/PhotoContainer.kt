package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import coil.api.load
import com.shevelev.my_footprints_remastered.R
import kotlinx.android.synthetic.main.view_photo_container.view.*

class PhotoContainer
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var onAddClickListener: (() -> Unit)? = null

    var state: PhotoContainerState = PhotoContainerState.Initial
    set(value) {
        field = value
        updateState()
    }

    init {
        inflate(context, R.layout.view_photo_container, this)

        addPhotoButton.setOnClickListener { onAddClickListener?.invoke() }
    }

    fun setOnAddClickListener(listener: (() -> Unit)?) {
        onAddClickListener = listener
    }

    private fun updateState() {
        when(state) {
            is PhotoContainerState.Initial -> {
                photoImage.visibility = View.VISIBLE
                photoFilter.visibility = View.VISIBLE
                addPhotoButton.visibility = View.VISIBLE
                loading.visibility = View.INVISIBLE
                clearButton.visibility = View.INVISIBLE
                editButton.visibility = View.INVISIBLE

                clearButton.isEnabled = false
                editButton.isEnabled = false
            }
            is PhotoContainerState.Loading -> {
                photoImage.visibility = View.VISIBLE
                photoFilter.visibility = View.VISIBLE
                addPhotoButton.visibility = View.INVISIBLE
                loading.visibility = View.VISIBLE
                clearButton.visibility = View.INVISIBLE
                editButton.visibility = View.INVISIBLE

                clearButton.isEnabled = false
                editButton.isEnabled = false
            }
            is PhotoContainerState.Updating -> {
                photoImage.visibility = View.VISIBLE
                photoFilter.visibility = View.INVISIBLE
                addPhotoButton.visibility = View.INVISIBLE
                loading.visibility = View.VISIBLE
                clearButton.visibility = View.VISIBLE
                editButton.visibility = View.VISIBLE

                clearButton.isEnabled = false
                editButton.isEnabled = false
            }
            is PhotoContainerState.Ready -> {
                photoImage.load((state as PhotoContainerState.Ready).image) {
                    crossfade(true)
                }

                photoImage.visibility = View.VISIBLE
                photoFilter.visibility = View.INVISIBLE
                addPhotoButton.visibility = View.INVISIBLE
                loading.visibility = View.INVISIBLE
                clearButton.visibility = View.VISIBLE
                editButton.visibility = View.VISIBLE

                clearButton.isEnabled = true
                editButton.isEnabled = true
            }
        }
    }
}