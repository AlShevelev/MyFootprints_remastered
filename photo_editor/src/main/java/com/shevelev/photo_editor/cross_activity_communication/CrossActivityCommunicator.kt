package com.shevelev.photo_editor.cross_activity_communication

import android.graphics.Bitmap

/**
 * Draft solution for cross-activity communication (because we can't pass a bitmap via Intent (bitmap size is too large))
 * In real code we should use DI
 */
object CrossActivityCommunicator {
    var bitmap: Bitmap? = null
}