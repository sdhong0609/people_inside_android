package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.mypage.EdittedUserInfo
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

class EditProfileViewModel(private val userService: UserService) : BaseViewModel() {

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

    @Suppress("UnusedPrivateMember")
    fun onNicknameTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        nickname.value = (s ?: "").toString()
        _nicknameCount.value = s?.length ?: 0
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
        viewModelScope.launch(exceptionHandler) {
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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val userService = RetrofitClient.userService
                return EditProfileViewModel(userService) as T
            }
        }
    }
}
