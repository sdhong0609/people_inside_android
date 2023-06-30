package com.beside153.peopleinside.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beside153.peopleinside.R
import com.beside153.peopleinside.util.Event

open class BaseViewModel : ViewModel() {

    val backButtonClickEvent = MutableLiveData<Event<Unit>>()

    private val _error = MutableLiveData<Event<Int>>()
    val error: LiveData<Event<Int>> get() = _error

    fun onBackButtonClick() {
        backButtonClickEvent.value = Event(Unit)
    }

    protected fun handleException(t: Throwable) {
        when (t) {
            is Exception -> {
                _error.value = Event(R.string.not_found_page)
            }
        }
    }
}
