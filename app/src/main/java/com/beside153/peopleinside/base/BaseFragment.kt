package com.beside153.peopleinside.base

import androidx.fragment.app.Fragment
import com.beside153.peopleinside.model.common.ErrorMessage
import com.beside153.peopleinside.view.dialog.OneButtonDialog

open class BaseFragment : Fragment() {

    protected fun showErrorDialog(
        errorMessage: ErrorMessage,
        onClickRefreshButton: () -> Unit
    ) {
        val errorDialog = OneButtonDialog.OneButtonDialogBuilder()
            .setErrorMessage(errorMessage)
            .setButtonClickListener(object : OneButtonDialog.OneButtonDialogListener {
                override fun onDialogButtonClick() {
                    onClickRefreshButton()
                }
            }).create()
        errorDialog.show(childFragmentManager, errorDialog.tag)
    }
}
