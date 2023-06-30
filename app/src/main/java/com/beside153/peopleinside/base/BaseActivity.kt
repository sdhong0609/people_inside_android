package com.beside153.peopleinside.base

import androidx.appcompat.app.AppCompatActivity
import com.beside153.peopleinside.view.error.ErrorDialog

open class BaseActivity : AppCompatActivity() {

    protected fun showErrorDialog(onClickRefreshButton: () -> Unit) {
        val errorDialog = ErrorDialog.ErrorDialogBuilder()
            .setButtonClickListener(object : ErrorDialog.ErrorDialogListener {
                override fun onClickRefreshButton() {
                    onClickRefreshButton()
                }
            }).create()
        errorDialog.show(supportFragmentManager, errorDialog.tag)
    }
}
