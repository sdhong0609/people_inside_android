package com.beside153.peopleinside.viewmodel.contentdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.viewmodel.BaseViewModel

class CreateReviewViewModel : BaseViewModel() {

    val reviewText = MutableLiveData("")

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

    fun onCompleteButtonClick() {
        if ((reviewText.value ?: "").isNotEmpty()) {
            _completeButtonClickEvent.value = Event(Unit)
        }
    }
}
