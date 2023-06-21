package com.beside153.peopleinside.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.model.login.AuthRegisterRequest
import com.beside153.peopleinside.service.SignUpService
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.App
import kotlinx.coroutines.launch

class SignUpUserInfoViewModel(private val signUpService: SignUpService) : ViewModel() {
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

    private val _backButtonClickEvent = MutableLiveData<Event<Unit>>()
    val backButtonClickEvent: LiveData<Event<Unit>> get() = _backButtonClickEvent

    fun setAuthToken(token: String) {
        authToken.value = token
    }

    @Suppress("UnusedPrivateMember")
    fun onNicknameTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        nickname.value = (s ?: "").toString()
        _nicknameCount.value = s?.length ?: 0
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
        // TODO: 가입하기 버튼 클릭 시 닉네임 중복체크 로직 및 금칙어 체크 로직 구현 필요
        // TODO: 로딩 및 exceptionHandler 구현 필요
//        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, t ->
//
//        }

        if (_isDuplicate.value == false) {
            viewModelScope.launch {
                val response = signUpService.postAuthRegister(
                    "Bearer ${authToken.value}",
                    AuthRegisterRequest(
                        _selectedMbti.value ?: "",
                        nickname.value ?: "",
                        _selectedGender.value ?: "",
                        (_selectedYear.value ?: 0).toString(),
                        "kakao"
                    )
                )

                App.prefs.setString("JWT_TOKEN", response.jwtToken)
            }
            _signUpButtonClickEvent.value = Event(Unit)
        }
    }

    fun onBackButtonClick() {
        _backButtonClickEvent.value = Event(Unit)
    }

    private fun checkSignUpButtonEnable() {
        _isSignUpButtonEnable.value = (_nicknameCount.value ?: 0) > 0 && (_selectedMbti.value ?: "") != "선택"
    }
}
