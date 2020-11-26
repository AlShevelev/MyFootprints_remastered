package com.shevelev.my_footprints_remastered.sync.gd_sign_in

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential

interface GoogleDriveCredentials {
    val needToSingIn: Boolean

    fun getCredentials(): GoogleAccountCredential?
}