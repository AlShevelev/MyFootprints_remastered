package com.shevelev.my_footprints_remastered.ui.view_commands

import android.location.Location
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.GeoPoint
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import java.io.File

interface ViewCommand

// Text messages
class ShowMessageRes(@StringRes val textResId: Int): ViewCommand

class ShowMessageText(val text: String): ViewCommand

// Navigation
class MoveBack: ViewCommand

class MoveToCreateFootprint(
    val oldFootprint: Footprint?
) : ViewCommand

class MoveToGridGallery : ViewCommand

class MoveToMyWorld : ViewCommand

class MoveToSettings : ViewCommand

class MoveToOneGallery(
    val footprints: List<Footprint>,
    val currentIndex: Int
) : ViewCommand

class MoveToSetLocation(
    val oldFootprint: Footprint?,
    val isImageUpdated: Boolean?
) : ViewCommand

class MoveToSelectPhoto : ViewCommand

class MoveToCropPhoto(val photo: File): ViewCommand

class MoveToEditPhoto(val photo: File): ViewCommand

class MoveBackFromSetLocationToTitle : ViewCommand

class MoveBackFromSetLocationToPager : ViewCommand

class MoveBackFromPagerToTitle : ViewCommand

class MoveToMainScreen: ViewCommand

// Child fragments for SetLocationFragment
class AddMapFragment : ViewCommand

class AddStubFragment : ViewCommand

class OpenCamera : ViewCommand

class OpenGallery : ViewCommand

class OpenLocationSettings : ViewCommand

class OpenEmail (
    val emailTo: String
) : ViewCommand

class AskAboutGeolocation : ViewCommand

class AskAboutFootprintInterruption : ViewCommand

class AskAboutDelete : ViewCommand

class StartLoadingMap : ViewCommand

class MoveAndZoomMap(
    val zoomFactor: Float,
    val location: Location
) : ViewCommand

class ShowColorDialog(
    @ColorInt
    val textColor: Int,
    @ColorInt
    val backgroundColor: Int
) : ViewCommand

class ShowMapDialog(
    val pinColor: PinColor,
    val imageFile: File,
    val comment: String?,
    val location: GeoPoint
) : ViewCommand

class ShowGoogleDriveExplanationDialog: ViewCommand

class ShowGoogleDriveFailDialog: ViewCommand

class ShowOkDialog(val text: String): ViewCommand

class StartSharing(
    val footprint: Footprint
): ViewCommand

class StartSignInToGoogleDrive: ViewCommand

class ProcessSignInToGoogleDriveFail: ViewCommand
class ProcessSignInToGoogleDriveSuccess: ViewCommand