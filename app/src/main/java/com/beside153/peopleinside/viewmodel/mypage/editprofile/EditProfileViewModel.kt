package com.beside153.peopleinside.viewmodel.mypage.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.common.exception.ApiException
import com.beside153.peopleinside.model.editprofile.EdittedUserInfo
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userService: UserService
) : BaseViewModel() {

    val nickname = MutableLiveData("")

    private val _nicknameCount = MutableLiveData(0)
    val nicknameCount: LiveData<Int> get() = _nicknameCount

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

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

    private val _nicknameIsEmpty = MutableLiveData(false)
    val nicknameIsEmpty: LiveData<Boolean> get() = _nicknameIsEmpty

    private val _isDuplicate = MutableLiveData(false)
    val isDuplicate: LiveData<Boolean> get() = _isDuplicate

    private val _hasBadWord = MutableLiveData(false)
    val hasBadWord: LiveData<Boolean> get() = _hasBadWord

    fun onNicknameTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        nickname.value = (s ?: "").toString()
        _nicknameCount.value = s?.length ?: 0
        _isDuplicate.value = false
        _hasBadWord.value = false
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
    }

    fun setNickname(nickname: String) {
        this.nickname.value = nickname
    }

    fun setNicknameCount(count: Int) {
        _nicknameCount.value = count
    }

    fun onCompleteButtonClick() {
        val ceh = CoroutineExceptionHandler { context, t ->
            when (t) {
                is ApiException -> {
                    when (t.error.statusCode) {
                        400 -> {
                            _isDuplicate.value = true
                        }

                        403 -> {
                            _hasBadWord.value = true
                        }

                        else -> {
                            exceptionHandler.handleException(context, t)
                        }
                    }
                }

                else -> exceptionHandler.handleException(context, t)
            }
        }
        viewModelScope.launch(ceh) {
            if ((nickname.value?.length ?: 0) <= 0) {
                setNicknameIsEmpty(true)
                return@launch
            }

            userService.patchUserInfo(
                App.prefs.getUserId(),
                EdittedUserInfo(
                    nickname.value ?: "",
                    _selectedMbti.value ?: "",
                    _selectedYear.value.toString(),
                    _selectedGender.value ?: ""
                )
            )

            App.prefs.setNickname(nickname.value ?: "")
            App.prefs.setMbti(_selectedMbti.value ?: "")
            App.prefs.setBirth(_selectedYear.value.toString())
            App.prefs.setGender(_selectedGender.value ?: "")

            _completeButtonClickEvent.value = Event(Unit)
        }
    }

    fun setNicknameIsEmpty(isEmpty: Boolean) {
        _nicknameIsEmpty.value = isEmpty
    }
}
