package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.services.first_loading.FirstLoadingService
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessage
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessageReceiver
import com.shevelev.my_footprints_remastered.services.first_loading.ui_communication.FirstLoadingServiceMessageReceiverImpl
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase
import kotlinx.android.synthetic.main.fragment_loading_progress.*

class LoadingProgressFragment : FragmentBase() {
    companion object {
        fun newInstance() = LoadingProgressFragment()
    }

    // Use injection in VM!!!
    private val messagesReceiver = FirstLoadingServiceMessageReceiverImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        messagesReceiver.setOnMessageListener { message ->
            when(message) {
                is FirstLoadingServiceMessage.Progress ->  textProgress.text = "${message.current} from ${message.total}"
                is FirstLoadingServiceMessage.Complete ->  textProgress.text = "Done!"
            }
        }

        FirstLoadingService.start(requireContext(), messagesReceiver)
        FirstLoadingService.start(requireContext(), messagesReceiver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loading_progress, container, false)
    }
}