package com.beside153.peopleinside.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.DialogOneButtonBinding
import com.beside153.peopleinside.model.common.ErrorMessage

class OneButtonDialog : DialogFragment() {
    private lateinit var binding: DialogOneButtonBinding

    var titleRes: Int? = null
    var description: String? = null
    var descriptionRes: Int? = null
    var buttonTextRes: Int? = null
    var listener: OneButtonDialogListener? = null

    override fun getTheme(): Int = R.style.RoundedCornersDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_one_button, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogTitleTextView.text = getString(titleRes ?: R.string.error_happend)
        binding.dialogDescriptionTextView.text = description ?: getString(descriptionRes ?: R.string.not_found_page)
        binding.dialogButton.text = getString(buttonTextRes ?: R.string.refresh)

        binding.dialogButton.setOnClickListener {
            dismiss()
            listener?.onDialogButtonClick()
        }
    }

    class OneButtonDialogBuilder {

        private val dialog = OneButtonDialog()

        fun setTitleRes(@StringRes title: Int): OneButtonDialogBuilder {
            dialog.titleRes = title
            return this
        }

        fun setDescription(description: String): OneButtonDialogBuilder {
            dialog.description = description
            return this
        }

        fun setDescriptionRes(@StringRes descriptionRes: Int): OneButtonDialogBuilder {
            dialog.descriptionRes = descriptionRes
            return this
        }

        fun setButtonTextRes(@StringRes buttonTextRes: Int): OneButtonDialogBuilder {
            dialog.buttonTextRes = buttonTextRes
            return this
        }

        fun setErrorMessage(errorMessage: ErrorMessage): OneButtonDialogBuilder {
            dialog.description = errorMessage.message
            dialog.descriptionRes = errorMessage.messageRes
            return this
        }

        fun setButtonClickListener(listener: OneButtonDialogListener): OneButtonDialogBuilder {
            dialog.listener = listener
            return this
        }

        fun create(): OneButtonDialog {
            return dialog
        }
    }

    interface OneButtonDialogListener {
        fun onDialogButtonClick()
    }
}
