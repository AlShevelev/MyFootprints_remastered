package com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt

import android.util.Base64
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.GeoPoint
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.utils.date_time.toBytes
import com.shevelev.my_footprints_remastered.utils.date_time.toSplit
import com.shevelev.my_footprints_remastered.utils.date_time.toZoneDateTime
import com.shevelev.my_footprints_remastered.utils.date_time.toZonedDateTimeSplit
import java.nio.ByteBuffer
import javax.inject.Inject

/**
 * Encrypts/decrypts footprint data to set of strings for GoogleDrive
 */
class FootprintMetaGoogleDriveCryptImpl
@Inject
constructor() : FootprintMetaGoogleDriveCrypt {
    override fun encrypt(footprint: Footprint): FootprintMetaGoogleDrive {
        val created = footprint.created.toSplit().toBytes()

        val mainData =
            ByteBuffer.allocate(1 + created.size + Double.SIZE_BYTES * 2 + Int.SIZE_BYTES * 2)
                .put(created.size.toByte())
                .put(created)
                .putDouble(footprint.location.latitude)
                .putDouble(footprint.location.longitude)
                .putInt(footprint.pinColor.textColor)
                .putInt(footprint.pinColor.backgroundColor)
                .array()
                .toDriveString()

        val comment = footprint.comment?.take(100)?.chunked(25) ?: listOf()

        return FootprintMetaGoogleDrive(comment, mainData)
    }

    override fun decrypt(gdData: FootprintMetaGoogleDrive): Footprint {
        // Comment
        val comment = gdData.comment.takeIf { it.isNotEmpty() }?.joinToString(separator = "")

        val mainDataBytes = gdData.mainData.toBytes()

        // Created
        val createdLen = mainDataBytes[0].toInt()
        val createdBytes = ByteArray(createdLen)
        mainDataBytes.copyInto(createdBytes, 0, 1, createdLen+1)
        val created = createdBytes.toZonedDateTimeSplit().toZoneDateTime()

        var offset =  1 + createdLen
        val buffer = ByteBuffer.wrap(mainDataBytes)

        // Location
        val location = GeoPoint(
            buffer.getDouble(offset),                           // Latitude
            buffer.getDouble(offset + Double.SIZE_BYTES)        // Longitude
        )

        offset+=Double.SIZE_BYTES*2

        // Pin color
        val pinColor = PinColor(
            buffer.getInt(offset),                      // Text color
            buffer.getInt(offset + Int.SIZE_BYTES)      // Background color
        )

        return Footprint(
            id = 0,
            imageFileName = "",
            location = location,
            comment = comment,
            pinColor = pinColor,
            created = created,
            city = null,
            country = null,
            isGeoLoaded = false,
            googleDriveFileId = null
        )
    }

    private fun ByteArray.toDriveString() = Base64.encodeToString(this, Base64.DEFAULT)

    private fun String.toBytes() = Base64.decode(this, Base64.DEFAULT)
}