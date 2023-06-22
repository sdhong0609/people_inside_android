package com.beside153.peopleinside.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beside153.peopleinside.service.SearchService
import com.beside153.peopleinside.util.Event

@Suppress("UnusedPrivateMember")
class SearchViewModel(private val searchService: SearchService) : ViewModel() {

    private val _backButtonClickEvent = MutableLiveData<Event<Unit>>()
    val backButtonClickEvent: LiveData<Event<Unit>> get() = _backButtonClickEvent

    private val _searchCancelClickEvent = MutableLiveData<Event<Unit>>()
    val searchCancelClickEvent: LiveData<Event<Unit>> get() = _searchCancelClickEvent

    fun onBackButtonClick() {
        _backButtonClickEvent.value = Event(Unit)
    }

    fun onSearchCancelClick() {
        _searchCancelClickEvent.value = Event(Unit)
    }
}
