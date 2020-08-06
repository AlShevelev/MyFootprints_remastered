package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model

import android.net.Uri
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.model.ModelBase
import com.shevelev.my_footprints_remastered.ui.shared.recycler_view.versioned.VersionedListItem
import java.io.File

interface SelectPhotoFragmentModel : ModelBase {
    suspend fun getItems(): List<VersionedListItem>

    fun storeSelectedPhoto(photo: File)

    fun storeSelectedPhoto(photo: Uri)
}