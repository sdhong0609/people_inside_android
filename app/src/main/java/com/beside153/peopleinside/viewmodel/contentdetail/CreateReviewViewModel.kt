package com.beside153.peopleinside.viewmodel.contentdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.common.CreateContentRequest
import com.beside153.peopleinside.service.mediacontent.ReviewService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateReviewViewModel @Inject constructor(
    private val reviewService: ReviewService
) : BaseViewModel() {

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
                    reviewService.putReview(contentId, CreateContentRequest(reviewText.value ?: ""))
                } else {
                    reviewService.postReview(contentId, CreateContentRequest(reviewText.value ?: ""))
                }
                _completeButtonClickEvent.value = Event(Unit)
            }
        }
    }
}
