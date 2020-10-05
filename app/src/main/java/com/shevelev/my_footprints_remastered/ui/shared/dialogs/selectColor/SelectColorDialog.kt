package com.shevelev.my_footprints_remastered.ui.shared.dialogs.selectColor

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.shevelev.my_footprints_remastered.R
import com.shevelev.my_footprints_remastered.ui.shared.mvvm.view.FragmentBase
import com.shevelev.my_footprints_remastered.utils.resources.isPortrait

/**
 * Dialog for select color of text and its background
 */
class SelectColorDialog : DialogFragment() {

    companion object {
        const val REQUEST_CODE = 1812

        private const val ARG_TEXT_COLOR = "ARG_TEXT_COLOR"
        private const val ARG_BACKGROUND_COLOR = "ARG_BACKGROUND_COLOR"
        private const val ARG_SAMPLE_TEXT = "ARG_SAMPLE_TEXT"

        fun show(
            fragment: FragmentBase,
            @ColorInt
            textColor: Int,
            @ColorInt
            backgroundColor: Int,
            @StringRes
            sampleText: Int) {

            SelectColorDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TEXT_COLOR, textColor)
                    putInt(ARG_BACKGROUND_COLOR, backgroundColor)
                    putInt(ARG_SAMPLE_TEXT, sampleText)
                }
                show(fragment.childFragmentManager, null)
            }
        }
    }

    override fun onCreateDialog(savedState: Bundle?): Dialog {
        var dialogView: SelectColorDialogView? = null

        return activity?.let {
            val textColor = savedState?.getInt(ARG_TEXT_COLOR) ?: requireArguments().getInt(ARG_TEXT_COLOR)
            val backgroundColor = savedState?.getInt(ARG_BACKGROUND_COLOR) ?: requireArguments().getInt(ARG_BACKGROUND_COLOR)

            AlertDialog.Builder(it, R.style.App_Activity_Main_Dialog_Theme)
                .setTitle(R.string.pinColorSelect)
                .setCancelable(true)
                .also { dialog ->
                    // Inflate view
                    SelectColorDialogView(requireContext())
                        .apply {
                            dialogView = this
                            setSampleText(it.getString(requireArguments().getInt(ARG_SAMPLE_TEXT)))
                            text = textColor
                            background = backgroundColor
                            showSample = it.isPortrait()
                            dialog.setView(this)
                        }
                }
                .setPositiveButton(R.string.ok) { _, _ ->
                    (parentFragment as FragmentBase).onDialogResult(
                        false,
                        REQUEST_CODE,
                        TextColors(dialogView!!.text, dialogView!!.background))
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    (parentFragment as FragmentBase).onDialogResult(true, REQUEST_CODE, null)
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        dialog?.let {
            outState.putInt(ARG_TEXT_COLOR, it.findViewById<SelectColorGrid>(R.id.textColorGrid).selectedColor)
            outState.putInt(ARG_BACKGROUND_COLOR, it.findViewById<SelectColorGrid>(R.id.backgroundColorGrid).selectedColor)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        (parentFragment as FragmentBase).onDialogResult(true, REQUEST_CODE, null)
    }
}