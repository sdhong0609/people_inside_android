package com.beside153.peopleinside.viewmodel.onboarding.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.common.ErrorEnvelope
import com.beside153.peopleinside.model.auth.AuthRegisterRequest
import com.beside153.peopleinside.service.AuthService
import com.beside153.peopleinside.service.ErrorEnvelopeMapper
import com.beside153.peopleinside.util.Event
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.suspendOnError
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class SignUpUserInfoViewModel(private val authService: AuthService) : BaseViewModel() {
    private val authToken = MutableLiveData("")

    val nickname = MutableLiveData("")

    private val _nicknameCount = MutableLiveData(0)
    val nicknameCount: LiveData<Int> get() = _nicknameCount

    private val _isDuplicate = MutableLiveData(false)
    val isDuplicate: LiveData<Boolean> get() = _isDuplicate

    private val _birthYearClickEvent = MutableLiveData<Event<Unit>>()
    val birthYearClickEvent: LiveData<Event<Unit>> get() = _birthYearClickEvent

    private val _mbtiChoiceClickEvent = MutableLiveData<Event<Unit>>()
    val mbtiChoiceClickEvent: LiveData<Event<Unit>> get() = _mbtiChoiceClickEvent

    private val _selectedGender = MutableLiveData("")
    val selectedGender: LiveData<String> get() = _selectedGender

    private val _selectedYear = MutableLiveData(0)
    val selectedYear: LiveData<Int> get() = _selectedYear

    private val _selectedMbti = MutableLiveData("")
    val selectedMbti: LiveData<String> get() = _selectedMbti

    private val _isSignUpButtonEnable = MutableLiveData(false)
    val isSignUpButtonEnable: LiveData<Boolean> get() = _isSignUpButtonEnable

    private val _signUpButtonClickEvent = MutableLiveData<Event<Unit>>()
    val signUpButtonClickEvent: LiveData<Event<Unit>> get() = _signUpButtonClickEvent

    fun setAuthToken(token: String) {
        authToken.value = token
    }

    @Suppress("UnusedPrivateMember")
    fun onNicknameTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        nickname.value = (s ?: "").toString()
        _nicknameCount.value = s?.length ?: 0
        _isDuplicate.value = false
        checkSignUpButtonEnable()
    }

    fun onBirthYearClick() {
        _birthYearClickEvent.value = Event(Unit)
    }

    fun onMbtiChoiceClick() {
        _mbtiChoiceClickEvent.value = Event(Unit)
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

    @Suppress("ForbiddenComment")
    fun onSignUpButtonClick() {
        // TODO: 가입하기 버튼 클릭 시 금칙어 체크 로직 구현 필요

        viewModelScope.launch(exceptionHandler) {
            val response = authService.postAuthRegister(
                "Bearer ${authToken.value}",
                AuthRegisterRequest(
                    "kakao",
                    nickname.value ?: "",
                    _selectedMbti.value?.lowercase() ?: "",
                    (_selectedYear.value ?: 0).toString(),
                    _selectedGender.value ?: ""
                )
            )

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

                _signUpButtonClickEvent.value = Event(Unit)
            }.suspendOnError(ErrorEnvelopeMapper) {
                val errorEnvelope = Json.decodeFromString<ErrorEnvelope>(this.message)
                if (errorEnvelope.message == "닉네임이 이미 존재합니다.") {
                    _isDuplicate.value = true
                }
            }
        }
    }

    private fun checkSignUpButtonEnable() {
        _isSignUpButtonEnable.value = (_nicknameCount.value ?: 0) > 0 && (_selectedMbti.value ?: "") != "선택"
    }
}
