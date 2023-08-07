package com.beside153.peopleinside.viewmodel.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.util.Event

class CommunityViewModel : BaseViewModel() {
    private val _searchBarClickEvent = MutableLiveData<Event<Unit>>()
    val searchBarClickEvent: LiveData<Event<Unit>> = _searchBarClickEvent

    private val _writePostClickEvent = MutableLiveData<Event<Unit>>()
    val writePostClickEvent: LiveData<Event<Unit>> = _writePostClickEvent

    fun onCommunitySearchBarClick() {
        _searchBarClickEvent.value = Event(Unit)
    }

    fun onWritePostClick() {
        _writePostClickEvent.value = Event(Unit)
    }
}
