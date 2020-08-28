package com.shevelev.my_footprints_remastered.ui.shared.dialogs.selectColor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.utils.resources.getDimension

/**
 * One item to select a color
 */
class SelectColorGridItem
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val drawingRect = Rect(0, 0, 0, 0)
    private val drawingPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val markRect = Rect(0, 0, 0, 0)

    @ColorInt
    private var markColor: Int = Color.BLACK
    @ColorInt
    private var fillColor: Int = Color.WHITE
    @ColorInt
    private var strokeColor: Int = Color.BLACK

    private val markResId: Int = R.drawable.ic_ok_18_14

    val color: Int
        @ColorInt
        get() = fillColor

    var isSelectedColor: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    init {
        attrs?.let { retrieveAttributes(attrs, defStyleAttr) }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun retrieveAttributes(attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectColorGridItem)

        markColor = typedArray.getColor(R.styleable.SelectColorGridItem_markColor, Color.BLACK)
        fillColor = typedArray.getColor(R.styleable.SelectColorGridItem_fillColor, Color.WHITE)
        strokeColor = typedArray.getColor(R.styleable.SelectColorGridItem_strokeColor, Color.BLACK)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        drawingRect.right = width
        drawingRect.bottom = height

        val markRectSizeFactor = 0.5f
        markRect.left = (width * markRectSizeFactor / 2).toInt()
        markRect.top = (height * markRectSizeFactor / 2).toInt()
        markRect.right = markRect.left + (width * markRectSizeFactor).toInt()
        markRect.bottom = markRect.top + (height * markRectSizeFactor).toInt()
    }

    override fun onDraw(canvas: Canvas?) {
        if(canvas == null) {
            return
        }

        val cy = drawingRect.height()/2f
        val cx = drawingRect.width()/2f
        val radius = drawingRect.height()/2f

        drawingPaint.style = Paint.Style.FILL
        drawingPaint.color = fillColor
        canvas.drawCircle(cx, cy, radius, drawingPaint)

        val strokeWidth = context.getDimension(R.dimen.strokeThin)
        drawingPaint.style = Paint.Style.STROKE
        drawingPaint.color = strokeColor
        drawingPaint.strokeWidth = strokeWidth

        canvas.drawCircle(cx, cy, radius-strokeWidth, drawingPaint)

        if(isSelectedColor) {
            val mark = context.getDrawable(markResId)!!.mutate()
            mark.setTint(markColor)

            mark.bounds = markRect
            mark.draw(canvas)
        }
    }
}