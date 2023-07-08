package com.beside153.peopleinside.base

import androidx.fragment.app.Fragment
import com.beside153.peopleinside.view.error.ErrorDialog

open class BaseFragment : Fragment() {

    protected fun showErrorDialog(onClickRefreshButton: () -> Unit) {
        val errorDialog = ErrorDialog.ErrorDialogBuilder()
            .setButtonClickListener(object : ErrorDialog.ErrorDialogListener {
                override fun onClickRefreshButton() {
                    onClickRefreshButton()
                }
            }).create()
        errorDialog.show(childFragmentManager, errorDialog.tag)
    }
}
