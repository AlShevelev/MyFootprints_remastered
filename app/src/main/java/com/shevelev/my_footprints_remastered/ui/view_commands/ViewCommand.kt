package com.shevelev.my_footprints_remastered.ui.view_commands

import android.location.Location
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
object MoveBack: ViewCommand

class MoveToCreateFootprint(
    val oldFootprint: Footprint?
) : ViewCommand

object MoveToGridGallery : ViewCommand

object MoveToMyWorld : ViewCommand

object MoveToSettings : ViewCommand

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

object MoveBackFromSetLocationToTitle : ViewCommand

object MoveBackFromSetLocationToPager : ViewCommand

object MoveBackFromPagerToTitle : ViewCommand

object MoveToMainScreen: ViewCommand

// Child fragments for SetLocationFragment
object AddMapFragment : ViewCommand

object AddStubFragment : ViewCommand

object OpenCamera : ViewCommand

object OpenGallery : ViewCommand

object OpenLocationSettings : ViewCommand

class OpenEmail (
    val emailTo: String
) : ViewCommand

object AskAboutGeolocation : ViewCommand

object AskAboutFootprintInterruption : ViewCommand

object AskAboutDelete : ViewCommand

object StartLoadingMap : ViewCommand

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

object ShowGoogleDriveExplanationDialog: ViewCommand

object ShowGoogleDriveFailDialog: ViewCommand

class ShowOkDialog(val text: String): ViewCommand

class StartSharing(
    val footprint: Footprint
): ViewCommand

object StartSignInToGoogleDrive: ViewCommand

object  ProcessSignInToGoogleDriveFail: ViewCommand
object ProcessSignInToGoogleDriveSuccess: ViewCommand

object StartAnimation: ViewCommand
object StopAnimation: ViewCommand

object HideSoftKeyboard: ViewCommand