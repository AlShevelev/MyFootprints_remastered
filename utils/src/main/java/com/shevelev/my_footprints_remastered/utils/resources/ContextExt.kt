package com.shevelev.my_footprints_remastered.utils.resources

import android.content.Context
import androidx.annotation.StringRes
import java.text.MessageFormat

fun Context.getStringFormatted(@StringRes resId: Int, vararg args: Any): String {
    return MessageFormat.format(getString(resId), *args)
}
