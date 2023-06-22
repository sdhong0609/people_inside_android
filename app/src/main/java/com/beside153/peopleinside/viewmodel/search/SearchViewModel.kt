package com.beside153.peopleinside.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.model.search.SearchingTitleModel
import com.beside153.peopleinside.service.SearchService
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

@Suppress("UnusedPrivateMember")
class SearchViewModel(private val searchService: SearchService) : ViewModel() {

    val keyword = MutableLiveData("")

    private val _backButtonClickEvent = MutableLiveData<Event<Unit>>()
    val backButtonClickEvent: LiveData<Event<Unit>> get() = _backButtonClickEvent

    private val _searchCancelClickEvent = MutableLiveData<Event<Unit>>()
    val searchCancelClickEvent: LiveData<Event<Unit>> get() = _searchCancelClickEvent

    private val _searchingTitleList = MutableLiveData<List<SearchingTitleModel>>()
    val searchingTitleList: LiveData<List<SearchingTitleModel>> get() = _searchingTitleList

    fun onBackButtonClick() {
        _backButtonClickEvent.value = Event(Unit)
    }

    fun onSearchCancelClick() {
        _searchCancelClickEvent.value = Event(Unit)
    }

    private fun loadSearchingTitle() {
        // exceptionHandler 구현 필요

        viewModelScope.launch {
            _searchingTitleList.value = searchService.getSearchedTitleList(keyword.value ?: "")
        }
    }

    @Suppress("UnusedPrivateMember")
    fun onKeywordTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        keyword.value = (s ?: "").toString()
        loadSearchingTitle()
    }
}
