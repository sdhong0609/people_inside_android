package com.beside153.peopleinside.viewmodel.community

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.service.community.CommunityPostService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunitySearchViewModel @Inject constructor(
    private val communityPostService: CommunityPostService
) : BaseViewModel() {

    private val _keyword = MutableLiveData("")
    val keyword: LiveData<String> get() = _keyword

    private val _postItemClickEvent = MutableLiveData<Event<Long>>()
    val postItemClickEvent: LiveData<Event<Long>> get() = _postItemClickEvent

    private val _searchedPostList = MutableLiveData<List<CommunityPostModel>>(listOf())
    val searchedPostList: LiveData<List<CommunityPostModel>> get() = _searchedPostList

    private val _isSearched = MutableLiveData(false)
    val isSearched: LiveData<Boolean> get() = _isSearched

    private val _noResult = MutableLiveData(false)
    val noResult: LiveData<Boolean> get() = _noResult

    private var page = 1

    fun searchPostAction() {
        page = 1
        _noResult.value = false
        val word = _keyword.value ?: ""
        if (word.isEmpty()) return

        viewModelScope.launch(exceptionHandler) {
            _searchedPostList.value = communityPostService.getCommunityPostList(page, word)
            _isSearched.value = true

            if (_searchedPostList.value.isNullOrEmpty()) {
                _noResult.value = true
            }
        }
    }

    fun onPostItemClick(item: CommunityPostModel) {
        _postItemClickEvent.value = Event(item.postId)
    }

    fun afterKeywordTextChanged(editable: Editable?) {
        _keyword.value = editable.toString()
        if (editable.isNullOrEmpty()) {
            _isSearched.value = false
            _noResult.value = false
        }
    }

    fun onSearchCancelClick() {
        _keyword.value = ""
        _isSearched.value = false
    }
}
