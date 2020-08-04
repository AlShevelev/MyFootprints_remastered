package com.shevelev.my_footprints_remastered.ui.shared.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase

class OkDialog : DialogFragment() {
    companion object {
        const val REQUEST_CODE = 1642

        private const val TEXT_KEY = "TEXT_KEY"

        fun show(fragment: FragmentBase, @StringRes text: Int) {
            OkDialog().apply {
                arguments = Bundle().apply {
                    putInt(TEXT_KEY, text)
                }
                show(fragment.childFragmentManager, null)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            AlertDialog.Builder(it)
                .setMessage(requireArguments().getInt(TEXT_KEY))
                .setCancelable(true)
                .setPositiveButton(R.string.ok) { _, _ ->
                    (parentFragment as FragmentBase).onDialogResult(false, REQUEST_CODE, null)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCancel(dialog: DialogInterface) {
        (parentFragment as FragmentBase).onDialogResult(true, REQUEST_CODE, null)
    }
}