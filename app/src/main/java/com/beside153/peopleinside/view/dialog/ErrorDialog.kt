package com.beside153.peopleinside.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.DialogErrorBinding
import com.beside153.peopleinside.model.common.ErrorMessage

class ErrorDialog : DialogFragment() {
    private lateinit var binding: DialogErrorBinding

    var description: String? = null
    var descriptionRes: Int? = null
    var listener: ErrorDialogListener? = null

    override fun getTheme(): Int = R.style.RoundedCornersDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_error, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorDialogContentTextView.text = description ?: getString(descriptionRes ?: R.string.not_found_page)
        binding.refreshButton.setOnClickListener {
            dismiss()
            listener?.onClickRefreshButton()
        }
    }

    class ErrorDialogBuilder {

        private val dialog = ErrorDialog()

        fun setDescription(description: String): ErrorDialogBuilder {
            dialog.description = description
            return this
        }

        fun setDescriptionRes(@StringRes descriptionRes: Int): ErrorDialogBuilder {
            dialog.descriptionRes = descriptionRes
            return this
        }

        fun setErrorMessage(errorMessage: ErrorMessage): ErrorDialogBuilder {
            dialog.description = errorMessage.message
            dialog.descriptionRes = errorMessage.messageRes
            return this
        }

        fun setButtonClickListener(listener: ErrorDialogListener): ErrorDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun create(): ErrorDialog {
            return dialog
        }
    }

    interface ErrorDialogListener {
        fun onClickRefreshButton()
    }
}
