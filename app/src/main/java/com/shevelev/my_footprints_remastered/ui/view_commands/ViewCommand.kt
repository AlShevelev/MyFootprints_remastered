package com.shevelev.my_footprints_remastered.ui.view_commands

import androidx.annotation.StringRes
import java.io.File

interface ViewCommand

// Text messages
class ShowMessageRes(@StringRes val textResId: Int): ViewCommand
class ShowMessageText(val text: String): ViewCommand

// Navigation
class MoveBack: ViewCommand
class MoveToCreateFootprint(): ViewCommand
class MoveToSelectPhoto(): ViewCommand
class MoveToCropPhoto(val photo: File): ViewCommand
class MoveToEditPhoto(val photo: File): ViewCommand

class OpenCamera(): ViewCommand
class OpenGallery(): ViewCommand
