package com.beside153.peopleinside.viewmodel.mypage.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.user.ResonIdModel
import com.beside153.peopleinside.model.withdrawal.WithDrawalReasonModel
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.service.WithDrawalService
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

class DeleteAccountViewModel(private val userService: UserService, private val withDrawalService: WithDrawalService) :
    BaseViewModel() {

    private val _checkedAgreeDelete = MutableLiveData(false)
    val checkedAgreeDelete: LiveData<Boolean> get() = _checkedAgreeDelete

    private val _deleteAccountClickEvent = MutableLiveData<Event<Unit>>()
    val deleteAccountClickEvent: LiveData<Event<Unit>> get() = _deleteAccountClickEvent

    private val _deleteAccountSuccessEvent = MutableLiveData<Event<Unit>>()
    val deleteAccountSuccessEvent: LiveData<Event<Unit>> get() = _deleteAccountSuccessEvent

    private val _withDrawalReasonList = MutableLiveData<List<WithDrawalReasonModel>>()
    val withDrawalReasonList: LiveData<List<WithDrawalReasonModel>> get() = _withDrawalReasonList

    private var checkedReasonId = INITIAL_REASON_ID

    fun initReasonList() {
        viewModelScope.launch(exceptionHandler) {
            _withDrawalReasonList.value = withDrawalService.getWithDrawalReasonList()
            val updatedList = _withDrawalReasonList.value?.map {
                if (it.reasonId == INITIAL_REASON_ID) {
                    it.copy(checked = true)
                } else {
                    it
                }
            }
            _withDrawalReasonList.value = updatedList ?: emptyList()
        }
    }

    fun onAgreeDeleteClick() {
        _checkedAgreeDelete.value = _checkedAgreeDelete.value == false
    }

    fun onDeleteAccountClick() {
        _deleteAccountClickEvent.value = Event(Unit)
    }

    fun deleteAccount() {
        viewModelScope.launch(exceptionHandler) {
            userService.deleteUser(App.prefs.getUserId(), ResonIdModel(checkedReasonId))
            App.prefs.setUserId(0)
            App.prefs.setNickname("")
            _deleteAccountSuccessEvent.value = Event(Unit)
        }
    }

    fun onRadioButtonClick(item: WithDrawalReasonModel) {
        checkedReasonId = item.reasonId
        val updatedList = _withDrawalReasonList.value?.map {
            if (item == it) {
                it.copy(checked = true)
            } else {
                it.copy(checked = false)
            }
        }
        _withDrawalReasonList.value = updatedList ?: emptyList()
    }

    companion object {
        private const val INITIAL_REASON_ID = 1

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val userService = RetrofitClient.userService
                val withDrawalService = RetrofitClient.withDrawalService
                return DeleteAccountViewModel(userService, withDrawalService) as T
            }
        }
    }
}
