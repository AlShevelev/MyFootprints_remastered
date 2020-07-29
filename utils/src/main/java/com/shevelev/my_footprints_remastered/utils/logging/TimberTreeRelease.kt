package com.shevelev.my_footprints_remastered.utils.logging

import android.util.Log
import timber.log.Timber

class TimberTreeRelease(/*private val crashlytics: CrashlyticsFacade*/): Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if(t != null) {
            t.printStackTrace()
//            crashlytics.log(t)        // Temporary commented
            return
        }

        Log.println(priority, tag, message)
    }
}