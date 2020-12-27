package com.shevelev.my_footprints_remastered.sync.gd_sign_in

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.services.drive.DriveScopes
import com.shevelev.my_footprints_remastered.utils.di_scopes.FragmentScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@FragmentScope
class GoogleDriveSignInImpl
@Inject
constructor(
    private val appContext: Context,
    private val credentials: GoogleDriveCredentials
) : GoogleDriveSignIn {
    companion object {
        private const val SIGN_IN_REQUEST = 43481
    }

    private val statusFlow: MutableStateFlow<GoogleDriveSignInStatus?> = MutableStateFlow(null)
    override val status: Flow<GoogleDriveSignInStatus?>
        get() = statusFlow.asStateFlow()

    override fun startSignIn() {
        if (credentials.needToSingIn) {
            statusFlow.value = GoogleDriveSignInStatus.SHOW_EXPLANATION
        } else {
            statusFlow.value = GoogleDriveSignInStatus.SUCCESS
        }
    }

    override fun continueSignIn(fragment: Fragment) {
        if (credentials.needToSingIn) {
            val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(Scope(DriveScopes.DRIVE_FILE))
                .build()

            val client = GoogleSignIn.getClient(appContext, signInOptions)
            fragment.startActivityForResult(client.signInIntent, SIGN_IN_REQUEST)
        } else {
            statusFlow.value = GoogleDriveSignInStatus.SUCCESS
        }
    }

    override fun processSignInActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == SIGN_IN_REQUEST && resultCode == Activity.RESULT_OK) {
            statusFlow.value = GoogleDriveSignInStatus.SUCCESS
        } else {
            statusFlow.value = GoogleDriveSignInStatus.FAIL
        }
    }
}