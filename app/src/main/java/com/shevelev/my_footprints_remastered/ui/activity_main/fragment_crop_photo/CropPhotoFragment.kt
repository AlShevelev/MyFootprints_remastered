package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import com.isseiaoki.simplecropview.CropImageView
import com.isseiaoki.simplecropview.callback.CropCallback
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_create_footprint.model.data_bridge.CreateFootprintFragmentDataBridge
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_crop_photo.di.CropPhotoFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase
import kotlinx.android.synthetic.main.fragment_crop_photo.*
import java.io.File
import javax.inject.Inject

class CropPhotoFragment : FragmentBase() {
    companion object {
        const val FILE_PARAM_KEY = "FILE_PARAM_KEY"
    }

    private var croppingInProgress = false

    override val isBackHandlerEnabled: Boolean = true

    @Inject
    internal lateinit var navigation: MainActivityNavigation

    @Inject
    internal lateinit var dataBridge: CreateFootprintFragmentDataBridge

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_crop_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val file = File(requireArguments().getString(FILE_PARAM_KEY)!!)

        cropPhoto.load(file) {
            crossfade(true)
        }
        cropPhoto.setCropMode(CropImageView.CropMode.FREE)
        cropPhoto.setInitialFrameScale(0.75f)
        cropPhoto.setMinFrameSizeInDp(100)
        cropPhoto.setTouchPaddingInDp(10)
        cropPhoto.setCompressFormat(Bitmap.CompressFormat.PNG)
        cropPhoto.setCompressQuality(100)

        acceptButton.setOnClickListener { cropAndComplete() }
        cancelButton.setOnClickListener { complete() }
    }

    override fun inject(key: String) = App.injections.get<CropPhotoFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<CropPhotoFragmentComponent>(key)

    override fun onBackPressed() {
        complete()
    }

    private fun cropAndComplete() {
        if(croppingInProgress) {
            return
        }

        croppingInProgress = true
        cropPhoto.cropAsync(object: CropCallback{
            override fun onSuccess(cropped: Bitmap?) {
                croppingInProgress = false
                dataBridge.putPhoto(cropped!!)
                complete()
            }

            override fun onError(e: Throwable?) {
                showMessage(R.string.generalError)
                croppingInProgress = false
            }
        })
    }

    private fun complete() {
        if(!croppingInProgress) {
            navigation.moveBack(this)
        }
    }
}