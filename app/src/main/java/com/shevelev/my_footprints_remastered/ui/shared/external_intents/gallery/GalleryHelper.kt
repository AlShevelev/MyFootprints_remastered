package com.shevelev.my_footprints_remastered.ui.shared.external_intents.gallery

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

interface GalleryHelper {
    fun takeGalleryPhoto(fragment: Fragment): Boolean

    fun processGalleryPhotoResult(requestCode: Int, resultCode: Int, data: Intent?, successAction: (Uri) -> Unit): Boolean
}