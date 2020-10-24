package com.shevelev.my_footprints_remastered.ui.shared.external_intents.email

import androidx.fragment.app.Fragment

interface SendEmailHelper {
    fun startSendEmail(emailTo: String, fragment: Fragment)
}