package com.shevelev.my_footprints_remastered.utils.resources

import android.content.Context
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.MessageFormat

fun Context.getStringFormatted(@StringRes resId: Int, vararg args: Any): String {
    return MessageFormat.format(getString(resId), *args)
}

fun Context.getRawString(@RawRes resId: Int): String =
    try {
        resources.openRawResource(resId).use { inputStream ->
            ByteArrayOutputStream().use { outputStream ->
                var i = inputStream.read()
                while (i != -1) {
                    outputStream.write(i)
                    i = inputStream.read()
                }

                outputStream.toString()
            }
        }
    } catch (ex: IOException) {
        Timber.e(ex)
        throw ex
    }
