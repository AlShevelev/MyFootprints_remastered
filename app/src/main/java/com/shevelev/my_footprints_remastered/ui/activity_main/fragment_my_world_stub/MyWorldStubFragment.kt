package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_my_world_stub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase

class MyWorldStubFragment : FragmentBase() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_world_stub, container, false)
    }
}