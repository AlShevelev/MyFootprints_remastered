package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_page

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.api.load
import coil.request.CachePolicy
import coil.request.RequestDisposable
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase
import kotlinx.android.synthetic.main.fragment_gallery_page.*

class GalleryPageFragment : FragmentBase() {
    companion object {
        private const val ARG_IMAGE_URI = "ARG_IMAGE_URI"
        private const val ARG_USE_IMAGE_CACHE = "ARG_USE_IMAGE_CACHE"

        fun newInstance(imageUri: Uri, useImageCache: Boolean): GalleryPageFragment =
            GalleryPageFragment().apply { arguments = Bundle().apply {
                putParcelable(ARG_IMAGE_URI, imageUri)
                putBoolean(ARG_USE_IMAGE_CACHE, useImageCache)
            }}
    }

    private var imageDispose: RequestDisposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUri = requireArguments().getParcelable<Uri>(ARG_IMAGE_URI)
        val useImageCache = requireArguments().getBoolean(ARG_USE_IMAGE_CACHE)

        imageDispose = imageContainer.load(imageUri) {
            if(useImageCache) {
                memoryCachePolicy(CachePolicy.ENABLED)
            } else {
                memoryCachePolicy(CachePolicy.DISABLED)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageDispose?.takeIf { !it.isDisposed } ?.dispose()
    }
}