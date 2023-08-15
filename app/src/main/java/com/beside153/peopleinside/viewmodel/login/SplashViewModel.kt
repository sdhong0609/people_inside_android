package com.beside153.peopleinside.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.BuildConfig
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.common.exception.ApiException
import com.beside153.peopleinside.service.AppVersionService
import com.beside153.peopleinside.service.ReportService
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appVersionService: AppVersionService,
    private val reportService: ReportService,
    private val userService: UserService
) : BaseViewModel() {

    private val _onBoardingCompletedEvent = MutableLiveData<Event<Boolean>>()
    val onBoardingCompletedEvent: LiveData<Event<Boolean>> get() = _onBoardingCompletedEvent

    private val _updateAppEvent = MutableLiveData<Event<Unit>>()
    val updateAppEvent: LiveData<Event<Unit>> get() = _updateAppEvent

    private val _goToPlayStoreEvent = MutableLiveData<Event<Unit>>()
    val goToPlayStoreEvent: LiveData<Event<Unit>> get() = _goToPlayStoreEvent

    private val _noUserInfoEvent = MutableLiveData<Event<Unit>>()
    val noUserInfoEvent: LiveData<Event<Unit>> get() = _noUserInfoEvent

    private var requiredAppVersion = BuildConfig.VERSION_NAME

    fun getAllData() {
        val ceh = CoroutineExceptionHandler { context, t ->
            when (t) {
                is ApiException -> {
                    if (t.error.statusCode == 404) {
                        _noUserInfoEvent.value = Event(Unit)
                    } else {
                        exceptionHandler.handleException(context, t)
                    }
                }

                else -> {
                    exceptionHandler.handleException(context, t)
                }
            }
        }
        viewModelScope.launch(ceh) {
            val allReportList = reportService.getReportList()
            if (App.prefs.getJwtToken().isNotEmpty()) {
                requiredAppVersion = appVersionService.getAppVersionLatest("android").requiredVersionName
            }

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

            App.prefs.setReportList(Json.encodeToString(allReportList))

            if (onBoardingCompleted) {
                _onBoardingCompletedEvent.value = Event(true)
                return@launch
            }
            _onBoardingCompletedEvent.value = Event(false)
        }
    }

    fun onGoToPlayStoreButtonClick() {
        _goToPlayStoreEvent.value = Event(Unit)
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
