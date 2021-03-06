package com.shevelev.my_footprints_remastered.application.di

import android.app.Application
import android.content.Context
import com.shevelev.my_footprints_remastered.BuildConfig
import com.shevelev.my_footprints_remastered.storages.db.builder.DatabaseBuilder
import com.shevelev.my_footprints_remastered.storages.db.core.DbCore
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.crashlytics.CrashlyticsFacade
import com.shevelev.my_footprints_remastered.utils.di_scopes.ApplicationScope
import com.shevelev.my_footprints_remastered.utils.logging.TimberTreeDebug
import com.shevelev.my_footprints_remastered.utils.logging.TimberTreeRelease
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

@Module
class AppModule(
    private val app: Application
) {
    @Provides
    @ApplicationScope
    internal fun provideContext(): Context = app.applicationContext

    @Provides
    internal fun provideDispatchersProvider(): DispatchersProvider = object : DispatchersProvider {
        override val uiDispatcher: CoroutineDispatcher
            get() = Dispatchers.Main
        override val calculationsDispatcher: CoroutineDispatcher
            get() = Dispatchers.Default
        override val ioDispatcher: CoroutineDispatcher
            get() = Dispatchers.IO
    }

    @Provides
    internal fun provideTimberTree(crashlytics: CrashlyticsFacade): Timber.Tree =
        when(BuildConfig.FLAVOR) {
            "dev" -> TimberTreeDebug()
            "prod" -> TimberTreeRelease(crashlytics)
            else -> throw UnsupportedOperationException("This flavor is not supported: ${BuildConfig.FLAVOR}")
        }

    @Provides
    @ApplicationScope
    internal fun provideRoomDbCore(appContext: Context): DbCore = DatabaseBuilder.build(appContext)
}