package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_gallery_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.databinding.FragmentGalleryPageBinding
import com.shevelev.my_footprints_remastered.ui.shared.glide.GlideTarget
import com.shevelev.my_footprints_remastered.ui.shared.glide.clear
import com.shevelev.my_footprints_remastered.ui.shared.glide.load
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase
import java.io.File

class GalleryPageFragment : FragmentBase() {
    companion object {
        private const val ARG_IMAGE_FILE = "ARG_IMAGE_URI"
        private const val ARG_USE_IMAGE_CACHE = "ARG_USE_IMAGE_CACHE"

        fun newInstance(imageFile: File, useImageCache: Boolean): GalleryPageFragment =
            GalleryPageFragment().apply { arguments = Bundle().apply {
                putString(ARG_IMAGE_FILE, imageFile.absolutePath)
                putBoolean(ARG_USE_IMAGE_CACHE, useImageCache)
            }}
    }

    private var imageDispose: GlideTarget? = null

    private var binding: FragmentGalleryPageBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGalleryPageBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageFile = File(requireArguments().getString(ARG_IMAGE_FILE)!!)
        val useImageCache = requireArguments().getBoolean(ARG_USE_IMAGE_CACHE)

        imageDispose = binding!!.imageContainer.load(imageFile, skipMemoryCache = !useImageCache)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageDispose?.clear(requireContext())
        binding = null
    }
}