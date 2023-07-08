package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseViewModel

class DeleteAccountViewModel : BaseViewModel() {
    private val _checkedRadioId = MutableLiveData(R.id.radioTextView1)
    val checkedRadioId: LiveData<Int> get() = _checkedRadioId

    private val _checkedAgreeDelete = MutableLiveData(false)
    val checkedAgreeDelete: LiveData<Boolean> get() = _checkedAgreeDelete

    fun onRadioClick(id: Int) {
        _checkedRadioId.value = id
    }

    fun onAgreeDeleteClick() {
        _checkedAgreeDelete.value = _checkedAgreeDelete.value == false
    }

    fun deleteAccount() {
        //
    }
}
