package com.beside153.peopleinside.viewmodel.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.service.community.CommunityPostService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityPostService: CommunityPostService
) : BaseViewModel() {
    private val _searchBarClickEvent = MutableLiveData<Event<Unit>>()
    val searchBarClickEvent: LiveData<Event<Unit>> get() = _searchBarClickEvent

    private val _writePostClickEvent = MutableLiveData<Event<Unit>>()
    val writePostClickEvent: LiveData<Event<Unit>> get() = _writePostClickEvent

    private val _postList = MutableLiveData<List<CommunityPostModel>>()
    val postList: LiveData<List<CommunityPostModel>> get() = _postList

    private val _postItemClickEvent = MutableLiveData<Event<Long>>()
    val postItemClickEvent: LiveData<Event<Long>> get() = _postItemClickEvent

    private val _progressBarVisible = MutableLiveData(false)
    val progressBarVisible: LiveData<Boolean> get() = _progressBarVisible

    private var page = 1

    fun initPostList() {
        setProgressBarVisible(true)
        page = 1
        _postList.value = listOf()
        viewModelScope.launch(exceptionHandler) {
            val tempList = communityPostService.getCommunityPostList(page)
            val userMbti = App.prefs.getMbti().lowercase()

            val updatedList = tempList.map {
                if (it.mbtiList.contains(userMbti)) {
                    val mutableMbtiList = it.mbtiList.toMutableList()
                    mutableMbtiList.remove(userMbti)
                    mutableMbtiList.add(0, userMbti)
                    it.copy(mbtiList = mutableMbtiList.toList())
                } else {
                    it
                }
            }

            _postList.value = updatedList
        }
    }

    fun loadMorePostList() {
        viewModelScope.launch(exceptionHandler) {
            val newPostList = communityPostService.getCommunityPostList(++page)
            _postList.value = _postList.value?.plus(newPostList)
        }
    }

    fun onPostItemClick(item: CommunityPostModel) {
        _postItemClickEvent.value = Event(item.postId)
    }

    fun setProgressBarVisible(visible: Boolean) {
        _progressBarVisible.value = visible
    }

    fun onCommunitySearchBarClick() {
        _searchBarClickEvent.value = Event(Unit)
    }

    fun onWritePostClick() {
        _writePostClickEvent.value = Event(Unit)
    }
}
