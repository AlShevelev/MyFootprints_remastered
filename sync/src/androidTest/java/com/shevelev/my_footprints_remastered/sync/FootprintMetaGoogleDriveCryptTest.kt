package com.shevelev.my_footprints_remastered.sync

import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.threetenabp.AndroidThreeTen
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.GeoPoint
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt.FootprintMetaGoogleDriveCryptImpl
import org.junit.Assert.assertNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.ZonedDateTime

@RunWith(AndroidJUnit4::class)
class FootprintMetaGoogleDriveCryptTest {
    private lateinit var crypt: FootprintMetaGoogleDriveCryptImpl

    companion object {
        @BeforeClass
        @JvmStatic
        fun init() {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            AndroidThreeTen.init(context)
        }
    }

    @Before
    fun initTest() {
        crypt = FootprintMetaGoogleDriveCryptImpl()
    }

    @Test
    fun commentNull() {
        // Arrange
        val oldFootprint = getBaseFootprint().copy(comment = null)

        // Act
        val newFootprint = crypt.decrypt(crypt.encrypt(oldFootprint))

        // Assert
        assertNull(newFootprint.comment)
    }

    @Test
    fun commentEmpty() {
        // Arrange
        val oldFootprint = getBaseFootprint().copy(comment = "")

        // Act
        val newFootprint = crypt.decrypt(crypt.encrypt(oldFootprint))

        // Assert
        assertNull(newFootprint.comment)
    }

    @Test
    fun commentLessThanChunk() {
        // Arrange
        val oldFootprint = getBaseFootprint().copy(comment = "0123456789")  // Len is 10, chunk size is 25

        // Act
        val encrypted = crypt.encrypt(oldFootprint)
        val newFootprint = crypt.decrypt(encrypted)

        // Assert
        assertEquals(1, encrypted.comment.size)
        assertEquals("0123456789", newFootprint.comment)
    }

    @Test
    fun commentChunkMultiplicity() {
        // Arrange
        val oldFootprint = getBaseFootprint().copy(comment = "0123456789012345678901234")  // Len is 25, chunk size is 25

        // Act
        val encrypted = crypt.encrypt(oldFootprint)
        val newFootprint = crypt.decrypt(encrypted)

        // Assert
        assertEquals(1, encrypted.comment.size)
        assertEquals("0123456789012345678901234", newFootprint.comment)
    }

    @Test
    fun commentGreaterThanChunk() {
        // Arrange
        val oldFootprint = getBaseFootprint().copy(comment = "012345678901234567890123456789")  // Len is 30, chunk size is 25

        // Act
        val encrypted = crypt.encrypt(oldFootprint)
        val newFootprint = crypt.decrypt(encrypted)

        // Assert
        assertEquals(2, encrypted.comment.size)
        assertEquals("012345678901234567890123456789", newFootprint.comment)
    }

    @Test
    fun commentCut() {
        // Arrange
        // Len is 110, chunk size is 25
        val oldFootprint = getBaseFootprint().copy(comment = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789")

        // Act
        val encrypted = crypt.encrypt(oldFootprint)
        val newFootprint = crypt.decrypt(encrypted)

        // Assert
        assertEquals(4, encrypted.comment.size)
        assertEquals("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789", newFootprint.comment)
    }

    @Test
    fun location() {
        // Arrange
        val oldFootprint = getBaseFootprint().copy(location = GeoPoint(42.16, 19.78))

        // Act
        val newFootprint = crypt.decrypt(crypt.encrypt(oldFootprint))

        // Assert
        assertEquals(GeoPoint(42.16, 19.78), newFootprint.location)
    }

    @Test
    fun pinColor() {
        // Arrange
        val oldFootprint = getBaseFootprint().copy(pinColor = PinColor(Color.BLUE, Color.GREEN))

        // Act
        val newFootprint = crypt.decrypt(crypt.encrypt(oldFootprint))

        // Assert
        assertEquals(PinColor(Color.BLUE, Color.GREEN), newFootprint.pinColor)
    }

    @Test
    fun created() {
        // Arrange
        val created = ZonedDateTime.now()
        val oldFootprint = getBaseFootprint().copy(created = created)

        // Act
        val newFootprint = crypt.decrypt(crypt.encrypt(oldFootprint))

        // Assert
        assertEquals(created, newFootprint.created)
    }

    private fun getBaseFootprint() =
        Footprint(
            id = 0,
            imageFileName = "",
            location = GeoPoint(0.0, 0.0),
            comment = null,
            pinColor = PinColor(0, 0),
            created = ZonedDateTime.now(),
            city = null,
            country = null,
            isGeoLoaded = false,
            googleDriveFileId = null
        )
}