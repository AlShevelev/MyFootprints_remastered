package com.shevelev.my_footprints_remastered.ui.view_commands

import android.location.Location
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import java.io.File

interface ViewCommand

// Text messages
class ShowMessageRes(@StringRes val textResId: Int): ViewCommand

class ShowMessageText(val text: String): ViewCommand

// Navigation
class MoveBack: ViewCommand

data class MoveToCreateFootprint(
    val oldFootprint: Footprint?
) : ViewCommand

class MoveToGridGallery : ViewCommand

class MoveToMyWorld : ViewCommand

data class MoveToOneGallery(
    val footprints: List<Footprint>,
    val currentIndex: Int
) : ViewCommand

data class MoveToSetLocation(
    val oldFootprint: Footprint?,
    val isImageUpdated: Boolean?
) : ViewCommand

class MoveToSelectPhoto : ViewCommand

class MoveToCropPhoto(val photo: File): ViewCommand

class MoveToEditPhoto(val photo: File): ViewCommand

class MoveBackFromSetLocationToTitle : ViewCommand

class MoveBackFromSetLocationToPager : ViewCommand

class MoveBackFromPagerToTitle : ViewCommand

// Child fragments for SetLocationFragment
class AddMapFragment : ViewCommand

class AddStubFragment : ViewCommand

class OpenCamera : ViewCommand

class OpenGallery : ViewCommand

class OpenLocationSettings : ViewCommand

class AskAboutGeolocation : ViewCommand

class AskAboutFootprintInterruption : ViewCommand

class AskAboutDelete : ViewCommand

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

data class ShowMapDialog(
    val footprint: Footprint
) : ViewCommand

data class StartSharing(
    val footprint: Footprint
): ViewCommand