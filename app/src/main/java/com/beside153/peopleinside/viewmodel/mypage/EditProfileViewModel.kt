package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.util.Event

class EditProfileViewModel : BaseViewModel() {

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

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

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

    fun onCompleteButtonClick() {
        //
    }
}
