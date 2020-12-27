package com.shevelev.my_footprints_remastered.sync.gd_sign_in

import android.content.Intent
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow

interface GoogleDriveSignIn {
    val status: Flow<GoogleDriveSignInStatus?>

    fun startSignIn()

    fun continueSignIn(fragment: Fragment)

    fun processSignInActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}