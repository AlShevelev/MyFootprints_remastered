package com.shevelev.my_footprints_remastered.sync.sign_in

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.drive.DriveScopes
import javax.inject.Inject

class GoogleDriveCredentialsImpl
@Inject
constructor(
    private val appContext: Context
) : GoogleDriveCredentials {

    override val needToSingIn: Boolean
        get() = getAccount() == null

    override fun getCredentials(): GoogleAccountCredential? =
        getAccount()?.let { account ->
            val credentials = GoogleAccountCredential.usingOAuth2(appContext, listOf(DriveScopes.DRIVE_APPDATA))
            credentials.selectedAccount = account.account
            return@let credentials
        }

    private fun getAccount() = GoogleSignIn.getLastSignedInAccount(appContext)
}