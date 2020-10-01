package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.view

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.application.App
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.databinding.FragmentGalleryOneBinding
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.di.GalleryOneFragmentComponent
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_one.view_model.GalleryOneFragmentViewModel
import com.shevelev.my_footprints_remastered.ui.activity_main.navigation.MainActivityNavigation
import com.shevelev.my_footprints_remastered.ui.shared.dialogs.map.MapDialog
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBaseMVVM
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import kotlinx.android.synthetic.main.fragment_gallery_one.*
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class GalleryOneFragment : FragmentBaseMVVM<FragmentGalleryOneBinding, GalleryOneFragmentViewModel>() {
    @Inject
    internal lateinit var navigation: MainActivityNavigation

    override fun provideViewModelType(): Class<GalleryOneFragmentViewModel> = GalleryOneFragmentViewModel::class.java

    override fun layoutResId(): Int = R.layout.fragment_gallery_one

    override fun linkViewModel(binding: FragmentGalleryOneBinding, viewModel: GalleryOneFragmentViewModel) {
        binding.viewModel = viewModel
    }

    override fun inject(key: String) = App.injections.get<GalleryOneFragmentComponent>(key).inject(this)

    override fun releaseInjection(key: String) = App.injections.release<GalleryOneFragmentComponent>(key)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showMapDialog.setOnClickListener {
            val footprint = Footprint(
                id = IdUtil.generateLongId(),
                imageContentUri = Uri.parse("content://media/external_primary/images/media/261039"),
                latitude = 40.758896,
                longitude = -73.985130,
                comment = "it's a long way to Tiperarry",
                pinTextColor = Color.WHITE,
                pinBackgroundColor = Color.RED,
                created = ZonedDateTime.now()
            )
            
            MapDialog.show(this, footprint)
        }
    }
}