package com.beside153.peopleinside.view.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.DialogErrorBinding

class ErrorDialog(private val refreshDialogInterface: RefreshDialogInterface) : DialogFragment() {
    private lateinit var binding: DialogErrorBinding

    override fun getTheme(): Int = R.style.RoundedCornersDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_error, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refreshButton.setOnClickListener {
            refreshDialogInterface.onDialogRefreshButtonClick()
            dismiss()
        }
    }
}

interface RefreshDialogInterface {
    fun onDialogRefreshButtonClick()
}
