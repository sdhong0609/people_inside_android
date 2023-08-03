package com.beside153.peopleinside.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.service.ReportService
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SplashViewmodel @Inject constructor(
    private val reportService: ReportService,
    private val userService: UserService
) :
    BaseViewModel() {

    private val _onBoardingCompletedEvent = MutableLiveData<Event<Boolean>>()
    val onBoardingCompletedEvent: LiveData<Event<Boolean>> get() = _onBoardingCompletedEvent

    fun getAllData() {
        viewModelScope.launch(exceptionHandler) {
            val reportDeferred = async { reportService.getReportList() }
            val reportList = reportDeferred.await()

            var onBoardingCompleted = true
            if (App.prefs.getUserId() != 0 && App.prefs.getIsMember()) {
                val onBoardingDeferred = async { userService.getOnBoardingCompleted(App.prefs.getUserId()) }
                onBoardingCompleted = onBoardingDeferred.await()
            }

            App.prefs.setString(App.prefs.reportListKey, Json.encodeToString(reportList))

            if (onBoardingCompleted) {
                _onBoardingCompletedEvent.value = Event(true)
                return@launch
            }
            _onBoardingCompletedEvent.value = Event(false)
        }
    }
}
