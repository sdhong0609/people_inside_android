package com.beside153.peopleinside.viewmodel.contentdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.model.contentdetail.CommentModel
import com.beside153.peopleinside.model.contentdetail.ContentDetailModel
import com.beside153.peopleinside.service.ContentDetailService
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.contentdetail.ContentDetailScreenAdapter.ContentDetailScreenModel
import kotlinx.coroutines.launch

class ContentDetailViewModel(private val contentDetailService: ContentDetailService) : ViewModel() {
    private val _backButtonClickEvent = MutableLiveData<Event<Unit>>()
    val backButtonClickEvent: LiveData<Event<Unit>> get() = _backButtonClickEvent

    private val contentDetailItem = MutableLiveData<ContentDetailModel>()

    private val _screenList = MutableLiveData<List<ContentDetailScreenModel>>()
    val screenList: LiveData<List<ContentDetailScreenModel>> get() = _screenList

    @Suppress("MagicNumber")
    private val commentList = listOf(
        CommentModel(
            1,
            "ENTP / 미소님",
            "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미진진한 플롯이었어.리뷰 최대 세 줄까지 노출됩니다다다다다"
        ),
        CommentModel(
            2,
            "ENTP / 미소님",
            "이 드라마는 도전적이고 흥미진진한 플롯이었어."
        ),
        CommentModel(
            3,
            "ENTP / 미소님",
            "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미진진한 플롯이었어.리뷰"
        ),
        CommentModel(
            4,
            "ENTP / 미소님",
            "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미진진한 플롯이었어.리뷰 최대 세 줄까지 노출됩니다다다다다"
        ),
        CommentModel(
            5,
            "ENTP / 미소님",
            "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미진진한 플롯이었어.리뷰 최대 세 줄까지 노출됩니다다다다다"
        )
    )

    fun onBackButtonClick() {
        _backButtonClickEvent.value = Event(Unit)
    }

    fun loadContentDetail(contentId: Int) {
        // 로딩 및 ExceptionHandler 구현 필요

        @Suppress("SpreadOperator")
        viewModelScope.launch {
            contentDetailItem.value = contentDetailService.getContentDetail(contentId)

            _screenList.value = listOf(
                ContentDetailScreenModel.PosterView(contentDetailItem.value!!),
                ContentDetailScreenModel.ReviewView,
                ContentDetailScreenModel.InfoView,
                ContentDetailScreenModel.CommentsView,
                *commentList.map { ContentDetailScreenModel.CommentItem(it) }.toTypedArray()
            )
        }
    }
}
