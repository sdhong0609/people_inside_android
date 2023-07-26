package com.beside153.peopleinside.base

import androidx.appcompat.app.AppCompatActivity
import com.beside153.peopleinside.model.common.ErrorMessage
import com.beside153.peopleinside.view.dialog.ErrorDialog

open class BaseActivity : AppCompatActivity() {

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
        errorDialog.show(supportFragmentManager, errorDialog.tag)
    }
}
