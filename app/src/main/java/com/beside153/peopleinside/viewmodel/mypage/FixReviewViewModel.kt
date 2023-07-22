package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.mediacontent.review.CreateReviewRequest
import com.beside153.peopleinside.model.mycontent.RatedContentModel
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.mediacontent.ReviewService
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

class FixReviewViewModel(private val reviewService: ReviewService) : BaseViewModel() {
    val reviewText = MutableLiveData("")

    private val _completeButtonClickEvent = MutableLiveData<Event<RatedContentModel>>()
    val completeButtonClickEvent: LiveData<Event<RatedContentModel>> get() = _completeButtonClickEvent

    private val contentItem = MutableLiveData<RatedContentModel>()

    fun initContentItem(contentItem: RatedContentModel?) {
        this.contentItem.value = contentItem
        reviewText.value = contentItem?.review?.content ?: ""
    }

    fun onCompleteButtonClick() {
        viewModelScope.launch(exceptionHandler) {
            if ((reviewText.value ?: "").isNotEmpty()) {
                reviewService.putReview(
                    contentItem.value?.contentId ?: 0,
                    CreateReviewRequest(reviewText.value ?: "")
                )
                val updatedReview = contentItem.value?.review?.copy(content = reviewText.value!!)
                contentItem.value = contentItem.value?.copy(review = updatedReview)
                _completeButtonClickEvent.value = Event(contentItem.value!!)
            }
        }
    }

    fun getFixedItem(): RatedContentModel = contentItem.value!!

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val reviewService = RetrofitClient.reviewService
                return FixReviewViewModel(reviewService) as T
            }
        }
    }
}
