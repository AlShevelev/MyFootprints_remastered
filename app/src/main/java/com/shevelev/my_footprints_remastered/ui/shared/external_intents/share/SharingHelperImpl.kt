package com.shevelev.my_footprints_remastered.ui.shared.external_intents.share

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.shevelev.my_footprints_remastered.BuildConfig
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.storages.files.BitmapFilesHelper
import com.shevelev.my_footprints_remastered.ui.shared.Constants
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject

class SharingHelperImpl
@Inject
constructor(
    private val appContext: Context,
    private val filesHelper: BitmapFilesHelper
) : SharingHelper {
    override fun share(footprint: Footprint, fragment: Fragment) {
        val text = footprint.comment?.let {
            "$it\n${getGoogleMapUrl(footprint)}"
        } ?: getGoogleMapUrl(footprint)

        val photoFile = filesHelper.getOrCreateImageFile(footprint.imageFileName)
        val photoFileUri = FileProvider.getUriForFile(appContext, BuildConfig.APPLICATION_ID + ".file_provider", photoFile)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, photoFileUri)
            putExtra(Intent.EXTRA_TEXT, text)
            type = "*/*"
        }

        if (shareIntent.resolveActivity(appContext.packageManager) == null) {
            return
        }

        fragment.startActivity(Intent.createChooser(shareIntent, appContext.getString(R.string.sendTo)))
    }

    private fun getGoogleMapUrl(footprint: Footprint): String {
        val df = DecimalFormat.getNumberInstance() as DecimalFormat
        df.maximumFractionDigits = 8
        df.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)

        return "https://www.google.com/maps?api=1&map_action=map&center=${df.format(footprint.location.latitude)},${df.format(footprint.location.longitude)}&zoom=${Constants.MAP_START_ZOOM}&q=loc:${df.format(footprint.location.latitude)}+${df.format(footprint.location.longitude)}"
    }
}