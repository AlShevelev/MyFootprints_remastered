package com.shevelev.my_footprints_remastered.ui.shared.external_intents.email

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import com.shevelev.my_footprints_remastered.R
import javax.inject.Inject

class SendEmailHelperImpl
@Inject
constructor(
    private val appContext: Context
) : SendEmailHelper {

    override fun startSendEmail(emailTo: String, fragment: Fragment) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("mailto:$emailTo")
        }

        if (shareIntent.resolveActivity(appContext.packageManager) == null) {
            return
        }

        fragment.startActivity(Intent.createChooser(shareIntent, appContext.getString(R.string.sendTo)))
    }
}