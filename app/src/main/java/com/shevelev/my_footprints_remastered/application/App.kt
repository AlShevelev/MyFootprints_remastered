package com.shevelev.my_footprints_remastered.application

import android.annotation.SuppressLint
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.shevelev.my_footprints_remastered.application.di.AppComponent
import com.shevelev.my_footprints_remastered.di_storage.DependencyInjectionStorage
import com.shevelev.my_footprints_remastered.services.update_geo_worker.UpdateGeoWorkerManager
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import timber.log.Timber
import javax.inject.Inject

class App : Application() {
    @Inject
    internal lateinit var timberTree: Timber.Tree

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var injections : DependencyInjectionStorage
            private set
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        injections = DependencyInjectionStorage(this)
        injections.get<AppComponent>(IdUtil.generateStringId()).inject(this)

        Timber.plant(timberTree)

        UpdateGeoWorkerManager.setupWorker(this.applicationContext)
    }
}