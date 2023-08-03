package com.beside153.peopleinside.viewmodel.mypage.contents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.mycontent.BookmarkedContentModel
import com.beside153.peopleinside.service.MyContentService
import com.beside153.peopleinside.service.mediacontent.BookmarkService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkedContentsViewModel @Inject constructor(
    private val myContentService: MyContentService,
    private val bookmarkService: BookmarkService
) : BaseViewModel() {
    private val _bookmarkCount = MutableLiveData(0)
    val bookmarkCount: LiveData<Int> get() = _bookmarkCount

    private val _contentList = MutableLiveData<List<BookmarkedContentModel>>()
    val contentList: LiveData<List<BookmarkedContentModel>> get() = _contentList

    private val _bookmarkCreatedEvent = MutableLiveData<Event<Boolean>>()
    val bookmarkCreatedEvent: LiveData<Event<Boolean>> get() = _bookmarkCreatedEvent

    private var page = 1

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            val bookmarkCountDeferred = async { myContentService.getBookmarkedCount() }
            val contentListDeferred = async { myContentService.getBookmarkedContents(page) }

            _bookmarkCount.value = bookmarkCountDeferred.await()
            _contentList.value = contentListDeferred.await()
        }
    }

    fun loadMoreData() {
        viewModelScope.launch(exceptionHandler) {
            val newContentList = myContentService.getBookmarkedContents(++page)
            _contentList.value = _contentList.value?.plus(newContentList)
        }
    }

    fun onBookmarkClick(item: BookmarkedContentModel) {
        viewModelScope.launch(exceptionHandler) {
            val response = bookmarkService.postBookmarkStatus(item.contentId)

            val updatedList: List<BookmarkedContentModel>?
            if (response.toggleStatus == CREATED) {
                updatedList = _contentList.value?.map {
                    if (item == it) {
                        it.copy(bookmarked = true)
                    } else {
                        it
                    }
                }
                _bookmarkCreatedEvent.value = Event(true)
            } else {
                updatedList = _contentList.value?.map {
                    if (item == it) {
                        it.copy(bookmarked = false)
                    } else {
                        it
                    }
                }
                _bookmarkCreatedEvent.value = Event(false)
            }

            _contentList.value = updatedList ?: emptyList()
            _bookmarkCount.value = myContentService.getBookmarkedCount()
        }
    }

    companion object {
        private const val CREATED = "created"
    }
}
