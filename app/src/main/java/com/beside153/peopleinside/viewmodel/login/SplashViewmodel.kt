package com.beside153.peopleinside.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.BuildConfig
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.service.AppVersionService
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
    private val appVersionService: AppVersionService,
    private val reportService: ReportService,
    private val userService: UserService
) : BaseViewModel() {

    private val _onBoardingCompletedEvent = MutableLiveData<Event<Boolean>>()
    val onBoardingCompletedEvent: LiveData<Event<Boolean>> get() = _onBoardingCompletedEvent

    private val _updateAppEvent = MutableLiveData<Event<Unit>>()
    val updateAppEvent: LiveData<Event<Unit>> get() = _updateAppEvent

    fun getAllData() {
        viewModelScope.launch(exceptionHandler) {
            val reportDeferred = async { reportService.getReportList() }
            val appVersionDeferred = async { appVersionService.getAppVersionLatest("android") }

            val reportList = reportDeferred.await()
            val requiredAppVersion = appVersionDeferred.await().requiredVersionName

            val currentAppVersion = BuildConfig.VERSION_NAME

            if (isNeedUpdate(currentAppVersion, requiredAppVersion)) {
                _updateAppEvent.value = Event(Unit)
                return@launch
            }

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

    private fun isNeedUpdate(currentAppVersion: String, requiredAppVersion: String): Boolean {
        var isNeedUpdate = false
        val arrX = currentAppVersion.split(".")
        val arrY = requiredAppVersion.split(".")

        val length = maxOf(arrX.size, arrY.size)

        for (i in 0 until length) {
            val x: Int = try {
                arrX.getOrNull(i)?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                0
            }
            val y: Int = try {
                arrY.getOrNull(i)?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                0
            }

            if (x > y) {
                // 앱 버전이 큼
                isNeedUpdate = false
                break
            } else if (x < y) {
                // 비교 버전이 큼
                isNeedUpdate = true
                break
            } else {
                // 버전 동일
                isNeedUpdate = false
            }
        }
        return isNeedUpdate
    }
}
