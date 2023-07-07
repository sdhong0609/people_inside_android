package com.beside153.peopleinside.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beside153.peopleinside.util.Event

class SignUpTermsViewModel : ViewModel() {

    private val _didCheckAll = MutableLiveData(false)
    val didCheckAll: LiveData<Boolean> get() = _didCheckAll

    private val _didCheckFirst = MutableLiveData(false)
    val didCheckFirst: LiveData<Boolean> get() = _didCheckFirst

    private val _didCheckSecond = MutableLiveData(false)
    val didCheckSecond: LiveData<Boolean> get() = _didCheckSecond

    private val _didCheckThird = MutableLiveData(false)
    val didCheckThird: LiveData<Boolean> get() = _didCheckThird

    private val _seeTermsClickEvent = MutableLiveData<Event<Unit>>()
    val seeTermsClickEvent: LiveData<Event<Unit>> get() = _seeTermsClickEvent

    private val _seePrivacyPolicyClickEvent = MutableLiveData<Event<Unit>>()
    val seePrivacyPolicyClickEvent: LiveData<Event<Unit>> get() = _seePrivacyPolicyClickEvent

    private val _isNextButtonEnable = MutableLiveData(false)
    val isNextButtonEnable: LiveData<Boolean> get() = _isNextButtonEnable

    private val _nextButtonClickEvent = MutableLiveData<Event<Unit>>()
    val nextButtonClickEvent: LiveData<Event<Unit>> get() = _nextButtonClickEvent

    fun onAllClick() {
        if (_didCheckAll.value == false) {
            _didCheckAll.value = true
            _didCheckFirst.value = true
            _didCheckSecond.value = true
            _didCheckThird.value = true
        } else {
            _didCheckAll.value = false
            _didCheckFirst.value = false
            _didCheckSecond.value = false
            _didCheckThird.value = false
        }
        checkAllChecked()
    }

    fun onFirstClick() {
        _didCheckFirst.value = _didCheckFirst.value == false
        checkAllChecked()
    }

    fun onSecondClick() {
        _didCheckSecond.value = _didCheckSecond.value == false
        checkAllChecked()
    }

    fun onThirdClick() {
        _didCheckThird.value = _didCheckThird.value == false
        checkAllChecked()
    }

    private fun checkAllChecked() {
        if (_didCheckFirst.value == true && _didCheckSecond.value == true && _didCheckThird.value == true) {
            _didCheckAll.value = true
            _isNextButtonEnable.value = true
            return
        }
        _didCheckAll.value = false
        _isNextButtonEnable.value = false
    }

    fun onNextButtonClick() {
        _nextButtonClickEvent.value = Event(Unit)
    }

    fun onSeeTermsClick() {
        _seeTermsClickEvent.value = Event(Unit)
    }

    fun onSeePrivacyPolicyClick() {
        _seePrivacyPolicyClickEvent.value = Event(Unit)
    }
}
