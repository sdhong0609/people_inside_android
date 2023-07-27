package com.beside153.peopleinside.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beside153.peopleinside.R
import com.beside153.peopleinside.common.exception.ApiException
import com.beside153.peopleinside.model.common.ErrorMessage
import com.beside153.peopleinside.util.Event
import java.net.UnknownHostException
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    val backButtonClickEvent = MutableLiveData<Event<Unit>>()

    private val _error = MutableLiveData<Event<ErrorMessage>>()
    val error: LiveData<Event<ErrorMessage>> get() = _error

    fun onBackButtonClick() {
        backButtonClickEvent.value = Event(Unit)
    }

    protected val exceptionHandler = CoroutineExceptionHandler { _, t ->
        Timber.e(t)
        when (t) {
            is UnknownHostException -> {
                _error.value = Event(
                    ErrorMessage(messageRes = R.string.network_error)
                )
            }

            is ApiException -> {
                Timber.e(t.error.toString())
                _error.value = Event(
                    ErrorMessage(message = t.error.message)
                )
            }

            is Exception -> {
                _error.value = Event(
                    ErrorMessage(messageRes = R.string.not_found_page)
                )
            }
        }
    }
}
