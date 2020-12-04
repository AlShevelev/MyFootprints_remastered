package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase

class LoadingProgressFragment : FragmentBase() {
    companion object {
        fun newInstance() = LoadingProgressFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loading_progress, container, false)
    }
}