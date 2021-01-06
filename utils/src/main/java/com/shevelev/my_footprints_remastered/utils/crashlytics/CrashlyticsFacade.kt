package com.shevelev.my_footprints_remastered.utils.crashlytics

interface CrashlyticsFacade {
    /** */
    fun log(tag: String, string: String)

    /** */
    fun log(ex: Throwable)
}