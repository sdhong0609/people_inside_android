package com.beside153.peopleinside.base

import androidx.appcompat.app.AppCompatActivity
import com.beside153.peopleinside.model.common.ErrorMessage
import com.beside153.peopleinside.view.dialog.OneButtonDialog

open class BaseActivity : AppCompatActivity() {

    protected fun showErrorDialog(
        errorMessage: ErrorMessage,
        onButtonClick: (() -> Unit)? = null
    ) {
        val errorDialog = OneButtonDialog.OneButtonDialogBuilder()
            .setErrorMessage(errorMessage)
            .setButtonClickListener(object : OneButtonDialog.OneButtonDialogListener {
                override fun onDialogButtonClick() {
                    if (onButtonClick != null) {
                        onButtonClick()
                    }
                }
            }).create()
        errorDialog.show(supportFragmentManager, errorDialog.tag)
    }
}
