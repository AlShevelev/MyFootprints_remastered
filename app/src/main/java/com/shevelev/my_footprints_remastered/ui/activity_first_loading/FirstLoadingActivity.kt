package com.shevelev.my_footprints_remastered.ui.activity_first_loading

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentials
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.di.FirstLoadingActivityComponent
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_google_drive_sign_in.view.GoogleDriveSignInFragment
import com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.view.LoadingProgressFragment
import com.shevelev.my_footprints_remastered.ui.activity_main.MainActivity
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.ActivityBase
import dagger.Lazy
import javax.inject.Inject

class FirstLoadingActivity : ActivityBase() {
    @Inject
    internal lateinit var keyValueStorageFacade: Lazy<KeyValueStorageFacade>

    @Inject
    internal lateinit var googleDriveCredentials: GoogleDriveCredentials

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_loaing)

        initScreen()
    }

    override fun inject(key: String) = App.injections.get<FirstLoadingActivityComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<FirstLoadingActivityComponent>(key)

    fun onGoogleDriveSignInFail() = updateFragment { replace(R.id.fragmentContainer, GoogleDriveSignInFragment.newInstance()) }

    fun onGoogleDriveSignInSuccess() {
        if(keyValueStorageFacade.get().isStartLoadingCompleted()) {
            moveToMainScreen()
        } else {
            updateFragment { replace(R.id.fragmentContainer, LoadingProgressFragment.newInstance()) }
        }
    }

    fun moveToMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun initScreen() {
        if(googleDriveCredentials.needToSingIn) {
            updateFragment { add(R.id.fragmentContainer, GoogleDriveSignInFragment.newInstance()) }
        } else {
            if(keyValueStorageFacade.get().isStartLoadingCompleted()) {
                moveToMainScreen()
            } else {
                updateFragment { add(R.id.fragmentContainer, LoadingProgressFragment.newInstance()) }
            }
        }
    }

    private fun updateFragment(fragmentAction: FragmentTransaction.() -> Unit) {
        supportFragmentManager.beginTransaction().apply {
            fragmentAction(this)
            commit()
        }
    }
}