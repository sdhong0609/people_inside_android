package com.beside153.peopleinside.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.common.ErrorEnvelope
import com.beside153.peopleinside.service.ErrorEnvelopeMapper
import com.beside153.peopleinside.service.OnBoardingService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.SignUpService
import com.beside153.peopleinside.util.Event
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.suspendOnError
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class LoginViewModel(private val signUpService: SignUpService, private val onBoardingService: OnBoardingService) :
    BaseViewModel() {

    private val _kakaoLoginClickEvent = MutableLiveData<Event<Unit>>()
    val kakaoLoginClickEvent: LiveData<Event<Unit>> get() = _kakaoLoginClickEvent

    private val _goToSignUpEvent = MutableLiveData<Event<String>>()
    val goToSignUpEvent: LiveData<Event<String>> get() = _goToSignUpEvent

    private val _onBoardingCompletedEvent = MutableLiveData<Event<Boolean>>()
    val onBoardingCompletedEvent: LiveData<Event<Boolean>> get() = _onBoardingCompletedEvent

    private val _withoutLoginClickEvent = MutableLiveData<Event<Unit>>()
    val withoutLoginClickEvent: LiveData<Event<Unit>> get() = _withoutLoginClickEvent

    private var authToken = ""

    fun setAuthToken(token: String) {
        authToken = token
    }

    fun onKakaoLoginClick() {
        _kakaoLoginClickEvent.value = Event(Unit)
    }

    fun peopleInsideLogin() {
        viewModelScope.launch(exceptionHandler) {
            val response = signUpService.postLoginKakao("Bearer $authToken")
            response.onSuccess {
                val jwtToken = this.response.body()?.jwtToken!!
                val user = this.response.body()?.user!!

                App.prefs.setString(App.prefs.jwtTokenKey, jwtToken)
                App.prefs.setUserId(user.userId)
                App.prefs.setNickname(user.nickname)
                App.prefs.setMbti(user.mbti)
                App.prefs.setBirth(user.birth)
                App.prefs.setGender(user.sex)
                App.prefs.setIsMember(true)

                viewModelScope.launch(exceptionHandler) {
                    val onBoardingCompleted = onBoardingService.getOnBoardingCompleted(user.userId)

                    if (onBoardingCompleted) {
                        _onBoardingCompletedEvent.value = Event(true)
                    } else {
                        _onBoardingCompletedEvent.value = Event(false)
                    }
                }
            }.suspendOnError(ErrorEnvelopeMapper) {
                val errorEnvelope = Json.decodeFromString<ErrorEnvelope>(this.message)
                if (errorEnvelope.message == "Unauthorized") {
                    _goToSignUpEvent.value = Event(authToken)
                }
            }
        }
    }

    fun onWithoutLoginClick() {
        _withoutLoginClickEvent.value = Event(Unit)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val signUpService = RetrofitClient.signUpService
                val onBoardingService = RetrofitClient.onBoardingService
                return LoginViewModel(signUpService, onBoardingService) as T
            }
        }
    }
}
