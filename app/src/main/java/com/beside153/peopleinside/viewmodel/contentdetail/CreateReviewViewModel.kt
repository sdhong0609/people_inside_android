package com.beside153.peopleinside.viewmodel.contentdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.review.CreateReviewRequest
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.ReviewService
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

class CreateReviewViewModel(private val reviewService: ReviewService) : BaseViewModel() {

    val reviewText = MutableLiveData("")

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

    private var contentId = 0
    private var alreadyHadReview = false

    fun setContentId(id: Int) {
        contentId = id
    }

    fun setContent(content: String) {
        reviewText.value = content
        if (content.isNotEmpty()) {
            alreadyHadReview = true
        }
    }

    fun onCompleteButtonClick() {
        viewModelScope.launch(exceptionHandler) {
            if ((reviewText.value ?: "").isNotEmpty()) {
                if (alreadyHadReview) {
                    reviewService.putReview(contentId, CreateReviewRequest(reviewText.value ?: ""))
                } else {
                    reviewService.postReview(contentId, CreateReviewRequest(reviewText.value ?: ""))
                }
                _completeButtonClickEvent.value = Event(Unit)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val reviewService = RetrofitClient.reviewService
                return CreateReviewViewModel(reviewService) as T
            }
        }
    }
}
