package com.beside153.peopleinside.base

import androidx.fragment.app.Fragment
import com.beside153.peopleinside.model.common.ErrorMessage
import com.beside153.peopleinside.view.dialog.ErrorDialog

open class BaseFragment : Fragment() {

    protected fun showErrorDialog(
        errorMessage: ErrorMessage,
        onClickRefreshButton: () -> Unit
    ) {
        val errorDialog = ErrorDialog.ErrorDialogBuilder()
            .setErrorMessage(errorMessage)
            .setButtonClickListener(object : ErrorDialog.ErrorDialogListener {
                override fun onClickRefreshButton() {
                    onClickRefreshButton()
                }
            }).create()
        errorDialog.show(childFragmentManager, errorDialog.tag)
    }
}
