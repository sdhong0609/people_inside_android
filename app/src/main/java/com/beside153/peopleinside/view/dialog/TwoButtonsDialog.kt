package com.beside153.peopleinside.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.bindingadapter.isVisible
import com.beside153.peopleinside.databinding.DialogTwoButtonsBinding

class TwoButtonsDialog : DialogFragment() {
    private lateinit var binding: DialogTwoButtonsBinding

    var title: Int? = null
    var description: String? = null
    var descriptionRes: Int? = null
    var descriptionVisible: Boolean = true
    var yesTextRes: Int? = null
    var noTextRes: Int? = null
    var listener: TwoButtonsDialogListener? = null

    override fun getTheme(): Int = R.style.RoundedCornersDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_two_buttons, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogTitleTextView.text = getString(title ?: R.string.error_happend)
        binding.dialogDescriptionTextView.text = description ?: getString(descriptionRes ?: R.string.not_found_page)
        binding.dialogDescriptionTextView.isVisible(descriptionVisible)
        binding.positiveButton.text = getString(yesTextRes ?: R.string.yes)
        binding.negativeButton.text = getString(noTextRes ?: R.string.no)

        binding.positiveButton.setOnClickListener {
            dismiss()
            listener?.onClickPositiveButton()
        }
        binding.negativeButton.setOnClickListener {
            dismiss()
            listener?.onClickNegativeButton()
        }
    }

    class TwoButtonsDialogBuilder {

        private val dialog = TwoButtonsDialog()

        fun setTitle(@StringRes title: Int): TwoButtonsDialogBuilder {
            dialog.title = title
            return this
        }

        fun setDescription(description: String): TwoButtonsDialogBuilder {
            dialog.description = description
            return this
        }

        fun setDescriptionRes(@StringRes descriptionRes: Int): TwoButtonsDialogBuilder {
            dialog.descriptionRes = descriptionRes
            return this
        }

        fun setDescriptionVisible(descriptionVisible: Boolean): TwoButtonsDialogBuilder {
            dialog.descriptionVisible = descriptionVisible
            return this
        }

        fun setYesText(@StringRes yesTextRes: Int): TwoButtonsDialogBuilder {
            dialog.yesTextRes = yesTextRes
            return this
        }

        fun setNoText(@StringRes noTextRes: Int): TwoButtonsDialogBuilder {
            dialog.noTextRes = noTextRes
            return this
        }

        fun setButtonClickListener(listener: TwoButtonsDialogListener): TwoButtonsDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun create(): TwoButtonsDialog {
            return dialog
        }
    }

    interface TwoButtonsDialogListener {
        fun onClickPositiveButton()
        fun onClickNegativeButton()
    }
}
