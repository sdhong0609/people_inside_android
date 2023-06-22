package com.beside153.peopleinside.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.model.search.SearchTrendItem
import com.beside153.peopleinside.model.search.SearchedContentModel
import com.beside153.peopleinside.model.search.SearchingTitleModel
import com.beside153.peopleinside.service.SearchService
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.search.SearchScreenAdapter.SearchScreenModel
import kotlinx.coroutines.launch

class SearchViewModel(private val searchService: SearchService) : ViewModel() {

    val keyword = MutableLiveData("")

    private val _backButtonClickEvent = MutableLiveData<Event<Unit>>()
    val backButtonClickEvent: LiveData<Event<Unit>> get() = _backButtonClickEvent

    private val searchingTitleList = MutableLiveData<List<SearchingTitleModel>>()
    private val searchedContentList = MutableLiveData<List<SearchedContentModel>>()

    private val _screenList = MutableLiveData<List<SearchScreenModel>>()
    val screenList: LiveData<List<SearchScreenModel>> get() = _screenList

    @Suppress("MagicNumber")
    private val searchTrendList = listOf(
        SearchTrendItem(1, "1", "분노의 질주: 라이드 오어 다이"),
        SearchTrendItem(2, "2", "가디언즈 오브 갤럭시: Volume 3"),
        SearchTrendItem(3, "3", "분노의 질주: 더 얼티메이트"),
        SearchTrendItem(4, "4", "분노의 질주: 라이드 오어 다이"),
        SearchTrendItem(5, "5", "앤트맨과 와스프: 퀀텀매니아"),
        SearchTrendItem(6, "6", "가디언즈 오브 갤럭시: Volume 3"),
        SearchTrendItem(7, "7", "분노의 질주: 라이드 오어 다이"),
        SearchTrendItem(8, "8", "분노의 질주: 라이드 오어 다이"),
        SearchTrendItem(9, "9", "분노의 질주: 라이드 오어 다이"),
        SearchTrendItem(10, "10", "분노의 질주: 라이드 오어 다이")
    )

    fun onBackButtonClick() {
        _backButtonClickEvent.value = Event(Unit)
    }

    fun onSearchCancelClick() {
        keyword.value = ""
    }

    @Suppress("SpreadOperator")
    fun initSearchScreen() {
        _screenList.value = listOf(
            SearchScreenModel.SeenViewItem,
            SearchScreenModel.TrendViewItem,
            *searchTrendList.map { SearchScreenModel.TrendContentItem(it) }.toTypedArray()
        )
    }

    fun loadSearchingTitle() {
        // exceptionHandler 구현 필요

        viewModelScope.launch {
            if (keyword.value?.isNotEmpty() == true) {
                searchingTitleList.value = searchService.getSearchedTitleList(keyword.value ?: "")
                changeScreenWhenSearching()
                return@launch
            }
            initSearchScreen()
        }
    }

    fun searchContentAction() {
        // exceptionHandler 구현 필요

        viewModelScope.launch {
            if (keyword.value?.isNotEmpty() == true) {
                searchedContentList.value = searchService.getSearchedContentList(keyword.value ?: "", 1)
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
    }

    fun onSearchingTitleItemClick(item: SearchingTitleModel) {
        keyword.value = item.title
    }
}
