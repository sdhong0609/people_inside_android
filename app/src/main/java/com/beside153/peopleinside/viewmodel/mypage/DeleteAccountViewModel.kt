package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.report.ResonIdModel
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

class DeleteAccountViewModel(private val userService: UserService) : BaseViewModel() {
    private val _checkedRadioId = MutableLiveData(R.id.radioTextView1)
    val checkedRadioId: LiveData<Int> get() = _checkedRadioId

    private val _checkedAgreeDelete = MutableLiveData(false)
    val checkedAgreeDelete: LiveData<Boolean> get() = _checkedAgreeDelete

    private val _deleteAccountClickEvent = MutableLiveData<Event<Unit>>()
    val deleteAccountClickEvent: LiveData<Event<Unit>> get() = _deleteAccountClickEvent

    private val _deleteAccountSuccessEvent = MutableLiveData<Event<Unit>>()
    val deleteAccountSuccessEvent: LiveData<Event<Unit>> get() = _deleteAccountSuccessEvent

    fun onRadioClick(id: Int) {
        _checkedRadioId.value = id
    }

    fun onAgreeDeleteClick() {
        _checkedAgreeDelete.value = _checkedAgreeDelete.value == false
    }

    fun onDeleteAccountClick() {
        _deleteAccountClickEvent.value = Event(Unit)
    }

    fun deleteAccount() {
        viewModelScope.launch(exceptionHandler) {
            userService.deleteUser(App.prefs.getUserId(), ResonIdModel(TEMP_REASON_ID))
            App.prefs.setUserId(0)
            App.prefs.setNickname("")
            _deleteAccountSuccessEvent.value = Event(Unit)
        }
    }

    companion object {
        private const val TEMP_REASON_ID = 6

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val userService = RetrofitClient.userService
                return DeleteAccountViewModel(userService) as T
            }
        }
    }
}
