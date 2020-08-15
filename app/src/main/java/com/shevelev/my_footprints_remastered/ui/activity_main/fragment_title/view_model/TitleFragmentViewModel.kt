package com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.view_model

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view_model.ViewModelBase
import com.shevelev.my_footprints_remastered.ui.activity_main.fragment_title.model.TitleFragmentModel
import com.shevelev.my_footprints_remastered.ui.view_commands.MoveToCreateFootprint
import com.shevelev.my_footprints_remastered.utils.coroutines.DispatchersProvider
import com.shevelev.my_footprints_remastered.utils.resources.getStringFormatted
import com.shevelev.my_footprints_remastered.utils.strings.toUpper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.UnsupportedOperationException
import javax.inject.Inject

class TitleFragmentViewModel
@Inject
constructor(
    private val appContext: Context,
    dispatchersProvider: DispatchersProvider,
    model: TitleFragmentModel
) : ViewModelBase<TitleFragmentModel>(dispatchersProvider, model) {

    private val _total = MutableLiveData<String>(getTotalFootprintsText(0))
    val total = _total as LiveData<String>

    private val _lastFootprintUri = MutableLiveData<Uri>()
    val lastFootprintUri = _lastFootprintUri as LiveData<Uri>

    private val _loadingVisibility = MutableLiveData<Int>(View.VISIBLE)
    val loadingVisibility: LiveData<Int> = _loadingVisibility

    private val _lastFootprintVisibility = MutableLiveData<Int>(View.INVISIBLE)
    val lastFootprintVisibility: LiveData<Int> = _lastFootprintVisibility

    init {
        launch {
            model.titleData.totalFootprints.collect {
                _total.value = getTotalFootprintsText(it)
            }
        }

        launch {
            model.titleData.lastFootprintFileName.collect {
                _lastFootprintUri.value = getLastFootprintUri(it)
                _loadingVisibility.value = View.INVISIBLE
                _lastFootprintVisibility.value = View.VISIBLE
            }
        }

        launch {
            model.titleData.init()
        }
    }

    fun onNewFootprintClick() {
        sendCommand(MoveToCreateFootprint())
    }

    private fun getTotalFootprintsText(total: Int) = appContext.getStringFormatted(R.string.totalFootprints, total).toUpper()

    private fun getLastFootprintUri(fileName: String?): Uri =
        if(fileName == null) {
            Uri.parse("android.resource://${appContext.packageName}/drawable/img_title_empty")
        } else {
            throw UnsupportedOperationException("Footprint displaying is not supported yet!")
        }
}