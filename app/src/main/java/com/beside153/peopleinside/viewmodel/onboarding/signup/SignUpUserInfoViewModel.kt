package com.beside153.peopleinside.viewmodel.onboarding.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.common.exception.ApiException
import com.beside153.peopleinside.model.auth.AuthRegisterRequest
import com.beside153.peopleinside.service.AuthService
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SignUpUserInfoEvent {
    object BirthYearClick : SignUpUserInfoEvent
    object MbtiChoiceClick : SignUpUserInfoEvent
    object SignUpButtonClick : SignUpUserInfoEvent
}

@HiltViewModel
class SignUpUserInfoViewModel @Inject constructor(
    private val userService: UserService,
    private val authService: AuthService
) : BaseViewModel() {
    val nickname = MutableLiveData("")

    private val _nicknameCount = MutableLiveData(0)
    val nicknameCount: LiveData<Int> get() = _nicknameCount

    private val _isDuplicate = MutableLiveData(false)
    val isDuplicate: LiveData<Boolean> get() = _isDuplicate

    private val _hasBadWord = MutableLiveData(false)
    val hasBadWord: LiveData<Boolean> get() = _hasBadWord

    private val _selectedGender = MutableLiveData("")
    val selectedGender: LiveData<String> get() = _selectedGender

    private val _selectedYear = MutableLiveData(0)
    val selectedYear: LiveData<Int> get() = _selectedYear

    private val _selectedMbti = MutableLiveData("")
    val selectedMbti: LiveData<String> get() = _selectedMbti

    private val _isSignUpButtonEnable = MutableLiveData(false)
    val isSignUpButtonEnable: LiveData<Boolean> get() = _isSignUpButtonEnable

    private val _signUpUserInfoEvent = MutableLiveData<Event<SignUpUserInfoEvent>>()
    val signUpUserInfoEvent: LiveData<Event<SignUpUserInfoEvent>> = _signUpUserInfoEvent

    private var authToken = ""

    fun setAuthToken(token: String) {
        authToken = token
    }

    fun onNicknameTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        nickname.value = (s ?: "").toString()
        _nicknameCount.value = s?.length ?: 0
        _isDuplicate.value = false
        _hasBadWord.value = false
        checkSignUpButtonEnable()
    }

    fun onBirthYearClick() {
        _signUpUserInfoEvent.value = Event(SignUpUserInfoEvent.BirthYearClick)
    }

    fun onMbtiChoiceClick() {
        _signUpUserInfoEvent.value = Event(SignUpUserInfoEvent.MbtiChoiceClick)
    }

    fun setSelectedGender(gender: String) {
        _selectedGender.value = gender
    }

    fun setSelectedYear(year: Int) {
        _selectedYear.value = year
    }

    fun setSelectedMbti(mbti: String) {
        _selectedMbti.value = mbti
        checkSignUpButtonEnable()
    }

    fun initNickname() {
        viewModelScope.launch(exceptionHandler) {
            nickname.value = userService.postRandomNickname().nickname
            _nicknameCount.value = nickname.value?.length ?: 0
        }
    }

    fun onSignUpButtonClick() {
        val ceh = CoroutineExceptionHandler { context, t ->
            when (t) {
                is ApiException -> {
                    when (t.error.statusCode) {
                        400 -> _isDuplicate.value = true
                        403 -> _hasBadWord.value = true
                        else -> exceptionHandler.handleException(context, t)
                    }
                }

                else -> exceptionHandler.handleException(context, t)
            }
        }

        viewModelScope.launch(ceh) {
            val response = authService.postAuthRegister(
                "Bearer $authToken",
                AuthRegisterRequest(
                    "kakao",
                    nickname.value ?: "",
                    _selectedMbti.value?.lowercase() ?: "",
                    (_selectedYear.value ?: 0).toString(),
                    _selectedGender.value ?: ""
                )
            )

            val jwtToken = response.jwtToken
            val user = response.user

            App.prefs.setJwtToken(jwtToken)
            App.prefs.setUserId(user.userId)
            App.prefs.setNickname(user.nickname)
            App.prefs.setMbti(user.mbti)
            App.prefs.setBirth(user.birth)
            App.prefs.setGender(user.sex)
            App.prefs.setIsMember(true)

            _signUpUserInfoEvent.value = Event(SignUpUserInfoEvent.SignUpButtonClick)
        }
    }

    private fun checkSignUpButtonEnable() {
        _isSignUpButtonEnable.value = (_nicknameCount.value ?: 0) > 0 && (_selectedMbti.value ?: "") != "선택"
    }
}
