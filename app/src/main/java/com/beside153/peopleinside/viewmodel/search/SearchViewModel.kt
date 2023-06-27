package com.beside153.peopleinside.viewmodel.search

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.model.search.SearchHotModel
import com.beside153.peopleinside.model.search.SearchedContentModel
import com.beside153.peopleinside.model.search.SearchingTitleModel
import com.beside153.peopleinside.model.search.ViewLogContentModel
import com.beside153.peopleinside.service.SearchService
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel
import com.beside153.peopleinside.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchViewModel(private val searchService: SearchService) : BaseViewModel() {

    private val _keyword = MutableLiveData("")
    val keyword: LiveData<String> get() = _keyword

    private val viewLogList = MutableLiveData<List<ViewLogContentModel>>()
    private val searchingTitleList = MutableLiveData<List<SearchingTitleModel>>()
    private val searchedContentList = MutableLiveData<List<SearchedContentModel>>()
    private val searchHotList = MutableLiveData<List<SearchHotModel>>()

    private val _screenList = MutableLiveData<List<SearchScreenModel>>()
    val screenList: LiveData<List<SearchScreenModel>> get() = _screenList

    private val _hideKeyboard = MutableLiveData<Event<Unit>>()
    val hideKeyboard: LiveData<Event<Unit>> get() = _hideKeyboard

    private var isSearching = false

    @Suppress("MagicNumber")
    private val exampleList = listOf(
        ViewLogContentModel(
            1,
            "스파이더맨: 노웨이 홈",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg"
        ),
        ViewLogContentModel(
            2,
            "스파이더맨: 노웨이 홈",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg"
        ),
        ViewLogContentModel(
            3,
            "스파이더맨: 노웨이 홈",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg"
        ),
        ViewLogContentModel(
            4,
            "스파이더맨: 노웨이 홈",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg"
        ),
        ViewLogContentModel(
            5,
            "스파이더맨: 노웨이 홈",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg"
        )
    )

    fun afterKeywordTextChanged(editable: Editable?) {
        _keyword.value = editable.toString()
        loadSearchingTitle()
    }

    fun onSearchCancelClick() {
        _keyword.value = ""
    }

    @Suppress("SpreadOperator")
    fun initSearchScreen() {
        // exceptionHandler 구현 필요

        viewModelScope.launch {
            val viwLogListDeferred = async { searchService.getViewLogList() }
            val searchHotListDeferred = async { searchService.getHotContentList() }

            viewLogList.value = viwLogListDeferred.await()
            searchHotList.value = searchHotListDeferred.await()

            val updatedList = searchHotList.value?.mapIndexed { index, item ->
                item.copy(rank = index + 1)
            }
            searchHotList.value = updatedList ?: emptyList()

            _screenList.value = listOf(
                SearchScreenModel.SeenView,
                *exampleList.map { SearchScreenModel.ViewLogItem(it) }.toTypedArray(),
                SearchScreenModel.HotView,
                *searchHotList.value?.map { SearchScreenModel.SearchHotItem(it) }?.toTypedArray() ?: emptyArray()
            )
        }
    }

    private fun loadSearchingTitle() {
        if (isSearching) {
            isSearching = false
            return
        }

        // exceptionHandler 구현 필요

        viewModelScope.launch {
            if (_keyword.value?.isNotEmpty() == true) {
                searchingTitleList.value = searchService.getSearchingTitleList(_keyword.value ?: "")
                changeScreenWhenSearching()
                return@launch
            }
            initSearchScreen()
        }
    }

    fun searchContentAction() {
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            changeScreenWhenNoResult()
        }

        viewModelScope.launch(exceptionHandler) {
            if (_keyword.value?.isNotEmpty() == true) {
                searchedContentList.value = searchService.getSearchedContentList(_keyword.value ?: "", 1)
                if ((searchedContentList.value ?: emptyList()).isEmpty()) {
                    changeScreenWhenNoResult()
                    return@launch
                }
                changeScreenWhenSearchedContent()
            }
        }
    }

    @Suppress("SpreadOperator")
    private fun changeScreenWhenSearching() {
        _screenList.value = listOf(
            *searchingTitleList.value?.map { SearchScreenModel.SearchingTitleItem(it) }?.toTypedArray()
                ?: emptyArray()
        )
    }

    @Suppress("SpreadOperator")
    private fun changeScreenWhenSearchedContent() {
        _screenList.value = listOf(
            *searchedContentList.value?.map { SearchScreenModel.SearchedContentItem(it) }?.toTypedArray()
                ?: emptyArray()
        )
        _hideKeyboard.value = Event(Unit)
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
