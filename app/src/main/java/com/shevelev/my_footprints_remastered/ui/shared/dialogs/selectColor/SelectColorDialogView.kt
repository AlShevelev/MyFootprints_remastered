package com.shevelev.my_footprints_remastered.ui.shared.dialogs.selectColor

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.shevelev.my_footprints_remastered.R
import kotlinx.android.synthetic.main.dialog_select_color.view.*

/**
 * Dialog view for color selection
 */
class SelectColorDialogView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    /**
     * Text color
     */
    var text: Int
    get() = textColorGrid.selectedColor
    set(value) {
        textColorGrid.selectedColor = value
    }

    var showSample = true
        set(value) {
            field = value
            sampleTextLabel.visibility = if(value) View.VISIBLE else View.GONE
        }

    /**
     * Background color
     */
    var background: Int
        get() = backgroundColorGrid.selectedColor
        set(value) {
            backgroundColorGrid.selectedColor = value
        }

    init {
        inflate(context, R.layout.dialog_select_color, this)

        textColorGrid.setOnColorChangeListener {
            sampleTextLabel.setTextColor(it)
        }

        backgroundColorGrid.setOnColorChangeListener {
            sampleTextLabel.setBackgroundColor(it)
        }

        sampleTextLabel.visibility = if(showSample) View.VISIBLE else View.GONE
    }

    fun setSampleText(text: String) {
        sampleTextLabel.text = text
    }
}