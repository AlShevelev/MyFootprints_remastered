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

        private const val TEXT_COLOR_KEY = "TEXT_COLOR_KEY"
        private const val BACKGROUND_COLOR_KEY = "BACKGROUND_COLOR_KEY"
        private const val SAMPLE_TEXT_KEY = "SAMPLE_TEXT_KEY"

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
                    putInt(TEXT_COLOR_KEY, textColor)
                    putInt(BACKGROUND_COLOR_KEY, backgroundColor)
                    putInt(SAMPLE_TEXT_KEY, sampleText)
                }
                show(fragment.childFragmentManager, null)
            }
        }
    }

    override fun onCreateDialog(savedState: Bundle?): Dialog {
        var dialogView: SelectColorDialogView? = null

        return activity?.let {
            val textColor = savedState?.getInt(TEXT_COLOR_KEY) ?: requireArguments().getInt(TEXT_COLOR_KEY)
            val backgroundColor = savedState?.getInt(BACKGROUND_COLOR_KEY) ?: requireArguments().getInt(BACKGROUND_COLOR_KEY)

            AlertDialog.Builder(it, R.style.App_Activity_Main_Dialog_Theme)
                .setTitle(R.string.pinColorSelect)
                .setCancelable(true)
                .also { dialog ->
                    // Inflate view
                    SelectColorDialogView(requireContext())
                        .apply {
                            dialogView = this
                            setSampleText(it.getString(requireArguments().getInt(SAMPLE_TEXT_KEY)))
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
            outState.putInt(TEXT_COLOR_KEY, it.findViewById<SelectColorGrid>(R.id.textColorGrid).selectedColor)
            outState.putInt(BACKGROUND_COLOR_KEY, it.findViewById<SelectColorGrid>(R.id.backgroundColorGrid).selectedColor)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        (parentFragment as FragmentBase).onDialogResult(true, REQUEST_CODE, null)
    }
}