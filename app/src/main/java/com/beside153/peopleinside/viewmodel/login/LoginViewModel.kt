package com.beside153.peopleinside.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.SignUpService
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

class LoginViewModel(private val signUpService: SignUpService) : BaseViewModel() {

    private val _kakaoLoginClickEvent = MutableLiveData<Event<Unit>>()
    val kakaoLoginClickEvent: LiveData<Event<Unit>> get() = _kakaoLoginClickEvent

    private val _goToSignUpEvent = MutableLiveData<Event<String>>()
    val goToSignUpEvent: LiveData<Event<String>> get() = _goToSignUpEvent

    private val _loginSuccessEvent = MutableLiveData<Event<Unit>>()
    val loginSuccessEvent: LiveData<Event<Unit>> get() = _loginSuccessEvent

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

            App.prefs.setString(App.prefs.jwtTokenKey, response.jwtToken)
            App.prefs.setUserId(response.user.userId)
            App.prefs.setNickname(response.user.nickname)
            App.prefs.setMbti(response.user.mbti)
            App.prefs.setBirth(response.user.birth)
            App.prefs.setGender(response.user.sex)
            _loginSuccessEvent.value = Event(Unit)

            // response가 제대로 오지 않고 token이 없다는 메시지가 오면 회원가입으로 이동
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val signUpService = RetrofitClient.signUpService
                return LoginViewModel(signUpService) as T
            }
        }
    }
}
