package com.beside153.peopleinside.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beside153.peopleinside.util.Event

class SignUpContentChoiceViewModel : ViewModel() {
    private val _choiceCount = MutableLiveData(0)
    val choiceCount: LiveData<Int> get() = _choiceCount

    private val _isCompleteButtonEnable = MutableLiveData(false)
    val isCompleteButtonEnable: LiveData<Boolean> get() = _isCompleteButtonEnable

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

    private val _backButtonClickEvent = MutableLiveData<Event<Unit>>()
    val backButtonClickEvent: LiveData<Event<Unit>> get() = _backButtonClickEvent

    fun onBackButtonClick() {
        _backButtonClickEvent.value = Event(Unit)
    }
}
