package com.shevelev.my_footprints_remastered.ui.shared.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase

class ConfirmationDialog : DialogFragment() {
    companion object {
        const val REQUEST_CODE = 4220

        private const val TEXT_KEY = "TEXT_KEY"
        private const val POSITIVE_BUTTON_KEY = "POSITIVE_BUTTON_KEY"
        private const val NEGATIVE_BUTTON_KEY = "NEGATIVE_BUTTON_KEY"

        fun show(
            fragment: FragmentBase,
            @StringRes text: Int,
            @StringRes  positiveButton: Int,
            @StringRes negativeButton: Int) {

            ConfirmationDialog().apply {
                arguments = Bundle().apply {
                    putInt(TEXT_KEY, text)
                    putInt(POSITIVE_BUTTON_KEY, positiveButton)
                    putInt(NEGATIVE_BUTTON_KEY, negativeButton)
                }
                show(fragment.childFragmentManager, null)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                .setMessage(requireArguments().getInt(TEXT_KEY))
                .setPositiveButton(requireArguments().getInt(POSITIVE_BUTTON_KEY)) { _, _ ->
                    (parentFragment as FragmentBase).onDialogResult(false, REQUEST_CODE, null)
                }
                .setNegativeButton(requireArguments().getInt(NEGATIVE_BUTTON_KEY)) { _, _ ->
                    (parentFragment as FragmentBase).onDialogResult(true, REQUEST_CODE, null)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCancel(dialog: DialogInterface) {
        (parentFragment as FragmentBase).onDialogResult(true, REQUEST_CODE, null)
    }
}