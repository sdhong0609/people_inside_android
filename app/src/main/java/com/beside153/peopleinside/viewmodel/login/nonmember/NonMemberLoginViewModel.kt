package com.beside153.peopleinside.viewmodel.login.nonmember

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.common.exception.ApiException
import com.beside153.peopleinside.service.AuthService
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface NonMemberLoginEvent {
    object KakaoLoginClick : NonMemberLoginEvent
    data class GoToSignUp(val authToken: String) : NonMemberLoginEvent
    data class OnBoardingCompleted(val isCompleted: Boolean) : NonMemberLoginEvent
}

@HiltViewModel
class NonMemberLoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val userService: UserService
) : BaseViewModel() {

    private val _nonMemberLoginEvent = MutableLiveData<Event<NonMemberLoginEvent>>()
    val nonMemberLoginEvent: LiveData<Event<NonMemberLoginEvent>> = _nonMemberLoginEvent

    private var authToken = ""

    fun setAuthToken(token: String) {
        authToken = token
    }

    fun onKakaoLoginClick() {
        _nonMemberLoginEvent.value = Event(NonMemberLoginEvent.KakaoLoginClick)
    }

    fun peopleInsideLogin() {
        val ceh = CoroutineExceptionHandler { context, t ->
            when (t) {
                is ApiException -> {
                    if (t.error.statusCode == 401) {
                        _nonMemberLoginEvent.value = Event(NonMemberLoginEvent.GoToSignUp(authToken))
                    } else {
                        exceptionHandler.handleException(context, t)
                    }
                }

                else -> exceptionHandler.handleException(context, t)
            }
        }

        viewModelScope.launch(ceh) {
            val response = authService.postLoginKakao("Bearer $authToken")
            val jwtToken = response.jwtToken
            val user = response.user

            App.prefs.setJwtToken(jwtToken)
            App.prefs.setUserId(user.userId)
            App.prefs.setNickname(user.nickname)
            App.prefs.setMbti(user.mbti)
            App.prefs.setBirth(user.birth)
            App.prefs.setGender(user.sex)
            App.prefs.setIsMember(true)

            val onBoardingCompleted = userService.getOnBoardingCompleted(user.userId)

            if (onBoardingCompleted) {
                _nonMemberLoginEvent.value = Event(NonMemberLoginEvent.OnBoardingCompleted(true))
            } else {
                _nonMemberLoginEvent.value = Event(NonMemberLoginEvent.OnBoardingCompleted(false))
            }
        }
    }
}
