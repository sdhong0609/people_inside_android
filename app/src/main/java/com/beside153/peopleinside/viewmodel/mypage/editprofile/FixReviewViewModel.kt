package com.beside153.peopleinside.viewmodel.mypage.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.common.CreateContentRequest
import com.beside153.peopleinside.model.mycontent.RatedContentModel
import com.beside153.peopleinside.service.mediacontent.ReviewService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixReviewViewModel @Inject constructor(
    private val reviewService: ReviewService
) : BaseViewModel() {
    val reviewText = MutableLiveData("")

    private val _completeButtonClickEvent = MutableLiveData<Event<RatedContentModel>>()
    val completeButtonClickEvent: LiveData<Event<RatedContentModel>> get() = _completeButtonClickEvent

    private var contentItem = RatedContentModel(0, "title", "overview", "posterPath", null, null)

    fun initContentItem(contentItem: RatedContentModel?) {
        this.contentItem = contentItem ?: this.contentItem
        reviewText.value = contentItem?.review?.content ?: ""
    }

    fun onCompleteButtonClick() {
        viewModelScope.launch(exceptionHandler) {
            if ((reviewText.value ?: "").isNotEmpty()) {
                reviewService.putReview(
                    contentItem.contentId,
                    CreateContentRequest(reviewText.value ?: "")
                )
                val updatedReview = contentItem.review?.copy(content = reviewText.value ?: "")
                contentItem = contentItem.copy(review = updatedReview)
                _completeButtonClickEvent.value = Event(contentItem)
            }
        }
    }

    fun getFixedItem(): RatedContentModel = contentItem
}
