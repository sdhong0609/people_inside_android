package com.beside153.peopleinside.viewmodel.contentdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.App
import com.beside153.peopleinside.model.contentdetail.ContentDetailModel
import com.beside153.peopleinside.model.contentdetail.ContentRatingModel
import com.beside153.peopleinside.model.contentdetail.ContentReviewModel
import com.beside153.peopleinside.service.BookmarkService
import com.beside153.peopleinside.service.ContentDetailService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.contentdetail.ContentDetailScreenAdapter.ContentDetailScreenModel
import com.beside153.peopleinside.viewmodel.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ContentDetailViewModel(
    private val contentDetailService: ContentDetailService,
    private val bookmarkService: BookmarkService
) : BaseViewModel() {

    private val _contentDetailItem = MutableLiveData<ContentDetailModel>()
    val contentDetailItem: LiveData<ContentDetailModel> get() = _contentDetailItem

    private val contentRatingItem = MutableLiveData<ContentRatingModel>()
    private val bookmarked = MutableLiveData(false)
    private val reviewList = MutableLiveData<List<ContentReviewModel>>()

    private val _screenList = MutableLiveData<List<ContentDetailScreenModel>>()
    val screenList: LiveData<List<ContentDetailScreenModel>> get() = _screenList

    private val _scrollEvent = MutableLiveData<Event<Unit>>()
    val scrollEvent: LiveData<Event<Unit>> get() = _scrollEvent

    private val _createReviewClickEvent = MutableLiveData<Event<Int>>()
    val createReviewClickEvent: LiveData<Event<Int>> get() = _createReviewClickEvent

    fun initAllData(contentId: Int, didClickComment: Boolean) {
        // 로딩 및 ExceptionHandler 구현 필요
        // 별점과 감상이 있는지 확인하는 api exceptionhandler는 별점, 감상 정보가 존재하지 않을 때의 분기처리를 해줘야 한다.

        viewModelScope.launch {
            val contentDetailItemDeferred = async { contentDetailService.getContentDetail(contentId) }
            val reviewListDeferred = async { contentDetailService.getContentReviewList(contentId, 1) }
            val contentRatingItemDeferred =
                async { contentDetailService.getContentRating(contentId, App.prefs.getInt(App.prefs.userIdKey)) }
            val bookmarkStatusDeferred = async { bookmarkService.getBookmarkStatus(contentId) }

            _contentDetailItem.value = contentDetailItemDeferred.await()
            contentRatingItem.value = contentRatingItemDeferred.await()
            reviewList.value = reviewListDeferred.await()
            bookmarked.value = bookmarkStatusDeferred.await()

            _screenList.value = screenList()
            if (didClickComment) _scrollEvent.value = Event(Unit)
        }
    }

    fun onBookmarkClick(contentId: Int) {
        bookmarked.value = bookmarked.value != true
        _screenList.value = screenList()
        viewModelScope.launch {
            bookmarkService.postBookmarkStatus(contentId)
        }
    }

    @Suppress("SpreadOperator")
    private fun screenList(): List<ContentDetailScreenModel> {
        return listOf(
            ContentDetailScreenModel.PosterView(_contentDetailItem.value!!),
            ContentDetailScreenModel.ReviewView(contentRatingItem.value!!, bookmarked.value!!),
            ContentDetailScreenModel.InfoView(_contentDetailItem.value!!),
            ContentDetailScreenModel.CommentsView,
            *reviewList.value?.map { ContentDetailScreenModel.ContentReviewItem(it) }?.toTypedArray()
                ?: emptyArray()
        )
    }

    fun onCreateReviewClick(contentId: Int) {
        _createReviewClickEvent.value = Event(contentId)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val contentDetailService = RetrofitClient.contentDetailService
                val bookmarkService = RetrofitClient.bookmarkService
                return ContentDetailViewModel(contentDetailService, bookmarkService) as T
            }
        }
    }
}
