package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_select_photo.model.photo_items_source

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class PhotoItemsSourceImpl
@Inject
constructor(
    private val appContext: Context
) : PhotoItemsSource {
    /**
     * Returns list of images in the phone gallery, sorted by lastItemDate in descending order
     */
    override fun getGalleryImagesUrls(): List<Uri> = getGalleryItems().sortedByDescending { it.second }.map { it.first }

    @SuppressLint("Recycle")
    private fun getGalleryItems(): List<Pair<Uri, Date>> {
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.MIME_TYPE)

        Timber.tag("LOAD_ITEMS").d("Start items query")
        appContext.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null)!!.use { cursor ->
            Timber.tag("LOAD_ITEMS").d("Query finished")
            val result = mutableListOf<Pair<Uri, Date>>()

            if (cursor.moveToFirst()) {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
                val mimeTypeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)

                do {
                    val mimeType = cursor.getString(mimeTypeColumn)
                    if(mimeType != "image/jpeg" && mimeType != "image/png") {
                        continue
                    }

                    val id = cursor.getLong(idColumn)
                    val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                    val dateAdded = cursor.getString(dateAddedColumn)
                    result.add(Pair(contentUri, Date(dateAdded.toLong())))
                } while (cursor.moveToNext())
            }

            return result
        }
    }
}