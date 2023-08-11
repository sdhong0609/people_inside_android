package com.beside153.peopleinside.viewmodel.community

import androidx.lifecycle.MutableLiveData
import com.beside153.peopleinside.base.BaseViewModel

class FixCommentViewModel : BaseViewModel() {

    private val _isCompleteButtonEnabled = MutableLiveData(false)
    val isCompleteButtonEnabled: MutableLiveData<Boolean> get() = _isCompleteButtonEnabled

    fun onCompleteButtonClick() {
        //
    }
}
