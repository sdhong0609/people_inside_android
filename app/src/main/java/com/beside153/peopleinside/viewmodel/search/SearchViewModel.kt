package com.beside153.peopleinside.viewmodel.search

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.mediacontent.SearchHotModel
import com.beside153.peopleinside.model.mediacontent.SearchedContentModel
import com.beside153.peopleinside.model.mediacontent.SearchingTitleModel
import com.beside153.peopleinside.model.mediacontent.ViewLogContentModel
import com.beside153.peopleinside.service.mediacontent.MediaContentService
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SearchViewModelHandler {
    val viewLogList: List<ViewLogContentModel>
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mediaContentService: MediaContentService
) : BaseViewModel(), SearchViewModelHandler {

    private val _keyword = MutableLiveData("")
    val keyword: LiveData<String> get() = _keyword

    private val _screenList = MutableLiveData<List<SearchScreenModel>>()
    val screenList: LiveData<List<SearchScreenModel>> get() = _screenList

    private val _searchCompleteEvent = MutableLiveData<Event<Unit>>()
    val searchCompleteEvent: LiveData<Event<Unit>> get() = _searchCompleteEvent

    private var isSearching = false
    private var page = 1
    private var searchingTitleList = listOf<SearchingTitleModel>()
    private var searchedContentList = listOf<SearchedContentModel>()
    private var searchHotList = listOf<SearchHotModel>()

    override var viewLogList = listOf<ViewLogContentModel>()

    fun afterKeywordTextChanged(editable: Editable?) {
        _keyword.value = editable.toString()
        loadSearchingTitle()
    }

    fun onSearchCancelClick() {
        _keyword.value = ""
        initSearchScreen()
    }

    fun initSearchScreen() {
        viewModelScope.launch(exceptionHandler) {
            val viwLogListDeferred = async { mediaContentService.getViewLogList() }
            val searchHotListDeferred = async { mediaContentService.getHotContentList() }

            viewLogList = viwLogListDeferred.await()
            searchHotList = searchHotListDeferred.await()

            val updatedList = searchHotList.mapIndexed { index, item ->
                item.copy(rank = index + 1)
            }
            searchHotList = updatedList

            if (viewLogList.isEmpty()) {
                _screenList.value = listOf(
                    SearchScreenModel.NoViewLogView,
                    SearchScreenModel.HotView,
                    *searchHotList.map { SearchScreenModel.SearchHotItem(it) }.toTypedArray()
                )
                return@launch
            }

            _screenList.value = listOf(
                SearchScreenModel.SeenView,
                SearchScreenModel.HotView,
                *searchHotList.map { SearchScreenModel.SearchHotItem(it) }.toTypedArray()
            )
        }
    }

    private fun loadSearchingTitle() {
        if (isSearching) {
            isSearching = false
            return
        }

        viewModelScope.launch(exceptionHandler) {
            if (_keyword.value?.isNotEmpty() == true) {
                searchingTitleList = mediaContentService.getSearchingTitleList(_keyword.value ?: "")
                changeScreenWhenSearching()
                return@launch
            }
            initSearchScreen()
        }
    }

    fun searchContentAction() {
        page = 1

        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            changeScreenWhenNoResult()
        }

        viewModelScope.launch(exceptionHandler) {
            if (_keyword.value?.isNotEmpty() == true) {
                searchedContentList = mediaContentService.getSearchedContentList(_keyword.value ?: "", page)
                if (searchedContentList.isEmpty()) {
                    changeScreenWhenNoResult()
                    return@launch
                }
                changeScreenWhenSearchedContent()
            }
        }
    }

    fun loadMoreContentList() {
        viewModelScope.launch(exceptionHandler) {
            if (_keyword.value?.isNotEmpty() == true) {
                val newContentList = mediaContentService.getSearchedContentList(_keyword.value ?: "", ++page)
                searchedContentList = searchedContentList.plus(newContentList)

                changeScreenWhenSearchedContent()
            }
        }
    }

    private fun changeScreenWhenSearching() {
        _screenList.value = listOf(
            *searchingTitleList.map { SearchScreenModel.SearchingTitleItem(it) }.toTypedArray()
        )
    }

    private fun changeScreenWhenSearchedContent() {
        _screenList.value = listOf(
            *searchedContentList.map { SearchScreenModel.SearchedContentItem(it) }.toTypedArray()
        )
        _searchCompleteEvent.value = Event(Unit)
    }

    private fun changeScreenWhenNoResult() {
        _screenList.value = listOf(SearchScreenModel.NoResultView)
    }

    fun onSearchingTitleItemClick(item: SearchingTitleModel) {
        isSearching = true
        _keyword.value = item.title
        searchContentAction()
    }
}
