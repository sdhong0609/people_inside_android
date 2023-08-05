package com.beside153.peopleinside.viewmodel.mypage.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.service.AppVersionService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val appVersionService: AppVersionService
) : BaseViewModel() {

    private val _appVersionName = MutableLiveData("1.0.0")
    val appVersionName: LiveData<String> get() = _appVersionName

    private val _termsClickEvent = MutableLiveData<Event<Unit>>()
    val termsClickEvent: LiveData<Event<Unit>> get() = _termsClickEvent

    private val _privacyPolicyClickEvent = MutableLiveData<Event<Unit>>()
    val privacyPolicyClickEvent: LiveData<Event<Unit>> get() = _privacyPolicyClickEvent

    private val _updateClickEvent = MutableLiveData<Event<Unit>>()
    val updateClickEvent: LiveData<Event<Unit>> get() = _updateClickEvent

    private val _logoutClickEvent = MutableLiveData<Event<Unit>>()
    val logoutClickEvent: LiveData<Event<Unit>> get() = _logoutClickEvent

    private val _deleteAccountEvent = MutableLiveData<Event<Unit>>()
    val deleteAccountEvent: LiveData<Event<Unit>> get() = _deleteAccountEvent

    fun setAppVersionName() {
        viewModelScope.launch(exceptionHandler) {
            _appVersionName.value = appVersionService.getAppVersionLatest("android").requiredVersionName
        }
    }

    fun onTermsClick() {
        _termsClickEvent.value = Event(Unit)
    }

    fun onPrivacyPolicyClick() {
        _privacyPolicyClickEvent.value = Event(Unit)
    }

    fun onUpdateClick() {
        _updateClickEvent.value = Event(Unit)
    }

    fun onLogoutClick() {
        _logoutClickEvent.value = Event(Unit)
    }

    fun onDeleteAccountClick() {
        _deleteAccountEvent.value = Event(Unit)
    }
}
