package com.beside153.peopleinside.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beside153.peopleinside.util.Event

open class BaseViewModel : ViewModel() {

    val backButtonClickEvent = MutableLiveData<Event<Unit>>()

    fun onBackButtonClick() {
        backButtonClickEvent.value = Event(Unit)
    }
}
