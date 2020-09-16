package com.shevelev.my_footprints_remastered.ui.shared.pin_draw

import android.content.Context
import android.graphics.*
import androidx.annotation.ColorInt
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.utils.resources.getDimension
import java.io.File
import javax.inject.Inject

class PinDrawImpl
@Inject
constructor(
    private val appContext: Context
) : PinDraw {

    private data class MeasuredText(
        val text: String,
        val width: Int,
        val height: Int,
        val baselineOffset: Int
    )

    private val scaleFactor = 1f

    // Sizes [px]
    private val height = 410.scale()
    private val widthMax = 990.scale()

    private val pinWidth = 330.scale()
    private val pinImageSize = 300.scale()
    private val pinSpearheadWidth = 40.scale()

    private val textMargin = 25.scale()
    private val textTagHeight = 80.scale()

    override fun draw(backgroundColor: Int, textColor: Int, imageFile: File?, text: String?): PinInfo {
        val image = cropImageToCircle(imageFile)

        return if(text.isNullOrBlank()) {
            drawWithoutText(image, backgroundColor)
        } else {
            drawWithText(image, text, backgroundColor, textColor)
        }
    }

    private fun drawWithoutText(image: Bitmap?, @ColorInt color: Int) : PinInfo {
        val output = Bitmap.createBitmap(pinWidth, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()

        drawPin(canvas, paint, color, pinWidth, height)
        drawImage(canvas, paint, image, pinWidth)

        return PinInfo(output, 0.5f)
    }

    private fun drawWithText(image: Bitmap?, text: String, @ColorInt backgroundColor: Int, @ColorInt textColor: Int) : PinInfo {
        val paint = Paint()
        paint.textSize = appContext.getDimension(R.dimen.textSizeSmall)

        val maxTextWidth = widthMax.scale() - pinWidth - textMargin * 2

        val measuredText = measureText(text, paint, maxTextWidth.toFloat())

        val width = pinWidth + textMargin*2 + measuredText.width + textTagHeight/2

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        drawPin(
            canvas,
            paint,
            backgroundColor,
            pinWidth,
            height)

        drawTag(
            canvas,
            paint,
            backgroundColor,
            textColor,
            pinWidth,
            textMargin,
            measuredText)

        drawImage(
            canvas,
            paint,
            image,
            pinWidth)

        return PinInfo(output, (pinWidth / width).toFloat())
    }

    /**
     * Calculates text length to inscribe in into the painted area
     * @return painted area len and inscribed
     */
    private fun measureText(text: String, paint: Paint, maxTextWidth: Float): MeasuredText {
        val textBounds = Rect()

        paint.getTextBounds(text, 0, text.length, textBounds)

        val textWidth = textBounds.width()

        if(textWidth <= maxTextWidth) {
            return MeasuredText(text, textBounds.width(), textBounds.height(), textBounds.bottom)
        }

        val oneCharWidth = textWidth / text.length
        val maxChars = (maxTextWidth / oneCharWidth).toInt()

        return MeasuredText(text.substring(0, maxChars-3)+"...", maxChars * oneCharWidth, textBounds.height(), textBounds.bottom)
    }

    private fun drawPin(canvas: Canvas, paint: Paint, @ColorInt color: Int, pinWidth: Int, pinHeight: Int) {
        paint.color = color
        paint.style = Paint.Style.FILL

        // The image background
        canvas.drawCircle(pinWidth/2f, pinWidth/2f, pinWidth/2f, paint)

        // The pin spearhead
        val spearheadPath = Path()
        spearheadPath.moveTo((pinWidth-pinSpearheadWidth)/2f, pinWidth*0.95f)
        spearheadPath.lineTo((pinWidth+pinSpearheadWidth)/2f, pinWidth*0.95f)
        spearheadPath.lineTo(pinWidth/2f, pinHeight.toFloat())
        spearheadPath.lineTo((pinWidth-pinSpearheadWidth)/2f, pinWidth*0.95f)
        canvas.drawPath(spearheadPath, paint)
    }

    private fun drawImage(canvas: Canvas, paint: Paint, image: Bitmap?, pinWidth: Int) {
        image?.let {
            val destOffset = (pinWidth - pinImageSize) / 2

            val imageDestRect = Rect(destOffset, destOffset, destOffset + pinImageSize, destOffset + pinImageSize)
            canvas.drawBitmap(it, Rect(0, 0, it.width, it.height), imageDestRect, paint)
        }
    }

    private fun drawTag(
        canvas: Canvas,
        paint: Paint,
        @ColorInt backgroundColor: Int,
        @ColorInt textColor: Int,
        pinWidth: Int,
        textMargin: Int,
        measuredText: MeasuredText) {

        val path = Path()
        paint.color = backgroundColor
        paint.style = Paint.Style.FILL

        val left = pinWidth*0.95f
        val leftOffset = pinWidth - left
        val right = left +leftOffset + textMargin + measuredText.width

        val top = (pinWidth - textTagHeight) / 2f
        val bottom = (pinWidth + textTagHeight) / 2f

        // Tag
        path.moveTo(left, top)
        path.lineTo(right, top)
        path.arcTo(right - textTagHeight/2f, top, right + textTagHeight/2f, bottom, 270f, 180f, false)
        path.lineTo(left, bottom)
        path.lineTo(left, top)
        canvas.drawPath(path, paint)

        paint.color = textColor
        canvas.drawText(
            measuredText.text,
            (pinWidth + textMargin).toFloat(),
            ((pinWidth + measuredText.height) / 2f) - measuredText.baselineOffset,
            paint)
    }

    private fun cropImageToCircle(imageFile: File?): Bitmap? =
        imageFile?.let {
            val source = BitmapFactory.decodeFile(it.absolutePath)

            val outputSize = source.width.coerceAtMost(source.height)
            val output = Bitmap.createBitmap(outputSize, outputSize, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(output)
            val paint = Paint()
            paint.isAntiAlias = true

            canvas.drawARGB(0, 0, 0, 0)
            canvas.drawCircle(outputSize/2f, outputSize/2f, outputSize/2f, paint)

            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

            val sourceRect = if(source.width > source.height) {
                val left = (source.width-outputSize)/2
                Rect(left, 0, source.width - left, outputSize)
            } else {
                val top = (source.height - outputSize) / 2
                Rect(0, top, outputSize, source.height - top)
            }
            canvas.drawBitmap(source, sourceRect, Rect(0, 0, outputSize, outputSize), paint)

            output
        }

    private fun Int.scale() = (this*scaleFactor).toInt() + 1
}