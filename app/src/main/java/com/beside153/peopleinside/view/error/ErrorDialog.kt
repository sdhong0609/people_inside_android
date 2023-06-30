package com.beside153.peopleinside.view.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.DialogErrorBinding

class ErrorDialog : DialogFragment() {
    private lateinit var binding: DialogErrorBinding

    var description: Int? = null
    var listener: ErrorDialogListener? = null

    override fun getTheme(): Int = R.style.RoundedCornersDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_error, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorDialogContentTextView.text = getString(description ?: R.string.not_found_page)
        binding.refreshButton.setOnClickListener {
            dismiss()
            listener?.onClickRefreshButton()
        }
    }

    class ErrorDialogBuilder {

        private val dialog = ErrorDialog()

        fun setDescription(@StringRes description: Int): ErrorDialogBuilder {
            dialog.description = description
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
