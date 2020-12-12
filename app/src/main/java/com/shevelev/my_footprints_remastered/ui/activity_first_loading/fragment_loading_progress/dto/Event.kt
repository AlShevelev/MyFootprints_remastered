package com.shevelev.my_footprints_remastered.ui.activity_first_loading.fragment_loading_progress.dto

sealed class Event {
    class ShowMessage(val text: String): Event()
    object MoveToMainScreen: Event()
    object ShowRestartButton: Event()
    object HideRestartButton: Event()
    class ShowWarningDialog(val text: String): Event()
}