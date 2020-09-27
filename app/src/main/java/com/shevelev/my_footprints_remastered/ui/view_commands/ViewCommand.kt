package com.shevelev.my_footprints_remastered.ui.view_commands

import android.location.Location
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.shevelev.my_footprints_remastered.ui.shared.pin_draw.PinDrawInfo
import java.io.File

interface ViewCommand

// Text messages
class ShowMessageRes(@StringRes val textResId: Int): ViewCommand
class ShowMessageText(val text: String): ViewCommand

// Navigation
class MoveBack: ViewCommand
class MoveToCreateFootprint : ViewCommand
class MoveToGridGallery : ViewCommand
class MoveToSetLocation : ViewCommand
class MoveToSelectPhoto : ViewCommand
class MoveToCropPhoto(val photo: File): ViewCommand
class MoveToEditPhoto(val photo: File): ViewCommand
class MoveBackFromSetLocationToTitle : ViewCommand

// Child fragments for SetLocationFragment
class AddMapForSetLocation(): ViewCommand
class AddStubForSetLocation(): ViewCommand

class OpenCamera : ViewCommand
class OpenGallery : ViewCommand
class OpenLocationSettings : ViewCommand

class AskAboutGeolocation : ViewCommand
class AskAboutFootprintInterruption : ViewCommand

class StartLoadingMap : ViewCommand

data class MoveAndZoomMap(
    val zoomFactor: Float,
    val location: Location
) : ViewCommand

data class ShowColorDialog(
    @ColorInt
    val textColor: Int,
    @ColorInt
    val backgroundColor: Int
) : ViewCommand