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

sealed interface SettingEvent {
    object TermsClick : SettingEvent
    object PrivacyPolicyClick : SettingEvent
    object UpdateClick : SettingEvent
    object LogoutClick : SettingEvent
    object DeleteAccountClick : SettingEvent
}

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val appVersionService: AppVersionService
) : BaseViewModel() {

    private val _appVersionName = MutableLiveData("1.0.0")
    val appVersionName: LiveData<String> get() = _appVersionName

    private val _settingEvent = MutableLiveData<Event<SettingEvent>>()
    val settingEvent: LiveData<Event<SettingEvent>> = _settingEvent

    fun setAppVersionName() {
        viewModelScope.launch(exceptionHandler) {
            _appVersionName.value = appVersionService.getAppVersionLatest("android").requiredVersionName
        }
    }

    fun onTermsClick() {
        _settingEvent.value = Event(SettingEvent.TermsClick)
    }

    fun onPrivacyPolicyClick() {
        _settingEvent.value = Event(SettingEvent.PrivacyPolicyClick)
    }

    fun onUpdateClick() {
        _settingEvent.value = Event(SettingEvent.UpdateClick)
    }

    fun onLogoutClick() {
        _settingEvent.value = Event(SettingEvent.LogoutClick)
    }

    fun onDeleteAccountClick() {
        _settingEvent.value = Event(SettingEvent.DeleteAccountClick)
    }
}
