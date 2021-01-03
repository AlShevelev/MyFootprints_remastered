package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.view.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ProgressBar
import com.github.chrisbanes.photoview.PhotoView
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.glide.load

class PhotoContainer
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var onAddClickListener: (() -> Unit)? = null
    private var onClearClickListener: (() -> Unit)? = null
    private var onCropClickListener: (() -> Unit)? = null
    private var onFilterClickListener: (() -> Unit)? = null

    var state: PhotoContainerState = PhotoContainerState.Initial
    set(value) {
        field = value
        updateState()
    }

    init {
        inflate(context, R.layout.view_photo_container, this)

        findViewById<Button>(R.id.addPhotoButton).setOnClickListener { onAddClickListener?.invoke() }
        findViewById<ImageButton>(R.id.clearPhotoButton).setOnClickListener { onClearClickListener?.invoke() }
        findViewById<ImageButton>(R.id.cropPhotoButton).setOnClickListener { onCropClickListener?.invoke() }
        findViewById<ImageButton>(R.id.filterPhotoButton).setOnClickListener { onFilterClickListener?.invoke() }

    }

    fun setOnAddClickListener(listener: (() -> Unit)?) {
        onAddClickListener = listener
    }

    fun setOnClearClickListener(listener: (() -> Unit)?) {
        onClearClickListener = listener
    }

    fun setOnCropClickListener(listener: (() -> Unit)?) {
        onCropClickListener = listener
    }

    fun setOnFilterClickListener(listener: (() -> Unit)?) {
        onFilterClickListener = listener
    }

    private fun updateState() {
        val photoImage = findViewById<PhotoView>(R.id.photoImage)
        val photoFilter = findViewById<View>(R.id.photoFilter)
        val addPhotoButton = findViewById<Button>(R.id.addPhotoButton)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val clearPhotoButton = findViewById<ImageButton>(R.id.clearPhotoButton)
        val cropPhotoButton = findViewById<ImageButton>(R.id.cropPhotoButton)
        val filterPhotoButton = findViewById<ImageButton>(R.id.filterPhotoButton)

        when(state) {
            is PhotoContainerState.Initial -> {
                photoImage.load(R.drawable.img_title_empty, crossFade = true)

                photoImage.visibility = View.VISIBLE
                photoFilter.visibility = View.VISIBLE
                addPhotoButton.visibility = View.VISIBLE
                loading.visibility = View.INVISIBLE
                clearPhotoButton.visibility = View.INVISIBLE
                cropPhotoButton.visibility = View.INVISIBLE
                filterPhotoButton.visibility = View.INVISIBLE
            }
            is PhotoContainerState.Loading -> {
                photoImage.visibility = View.VISIBLE
                photoFilter.visibility = View.VISIBLE
                addPhotoButton.visibility = View.INVISIBLE
                loading.visibility = View.VISIBLE
                clearPhotoButton.visibility = View.INVISIBLE
                cropPhotoButton.visibility = View.INVISIBLE
                filterPhotoButton.visibility = View.INVISIBLE
            }
            is PhotoContainerState.Ready -> {
                photoImage.load((state as PhotoContainerState.Ready).image, crossFade = true)

                photoImage.visibility = View.VISIBLE
                photoFilter.visibility = View.INVISIBLE
                addPhotoButton.visibility = View.INVISIBLE
                loading.visibility = View.INVISIBLE
                clearPhotoButton.visibility = View.VISIBLE
                cropPhotoButton.visibility = View.VISIBLE
                filterPhotoButton.visibility = View.VISIBLE
            }
        }
    }
}