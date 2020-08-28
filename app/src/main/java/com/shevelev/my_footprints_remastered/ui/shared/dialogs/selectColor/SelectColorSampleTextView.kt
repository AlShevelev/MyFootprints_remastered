package com.shevelev.my_footprints_remastered.ui.shared.dialogs.selectColor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.utils.resources.getDimension

class SelectColorSampleTextView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val drawPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val drawingRect = Rect(0, 0, 0, 0)

    @ColorInt
    private var background = Color.BLACK
        private set(value) {
            field = value
            invalidate()
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        drawingRect.right = w
        drawingRect.bottom = h
    }

    override fun onDraw(canvas: Canvas?) {
        if(canvas == null) {
            return
        }

        drawPaint.style = Paint.Style.FILL
        drawPaint.color = background
        canvas.drawRect(drawingRect, drawPaint)

        drawPaint.style = Paint.Style.STROKE
        drawPaint.color = context.getColor(R.color.gray)
        drawPaint.strokeWidth = context.getDimension(R.dimen.strokeNormal)
        canvas.drawRect(drawingRect, drawPaint)

        super.onDraw(canvas)
    }
}