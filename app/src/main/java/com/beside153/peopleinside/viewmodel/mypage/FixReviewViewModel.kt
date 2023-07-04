package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.contentdetail.CreateReviewRequest
import com.beside153.peopleinside.model.mypage.RatingContentModel
import com.beside153.peopleinside.service.RecommendService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

class FixReviewViewModel(private val recommendService: RecommendService) : BaseViewModel() {
    val reviewText = MutableLiveData("")

    private val _completeButtonClickEvent = MutableLiveData<Event<RatingContentModel>>()
    val completeButtonClickEvent: LiveData<Event<RatingContentModel>> get() = _completeButtonClickEvent

    private val contentItem = MutableLiveData<RatingContentModel>()

    fun initContentItem(contentItem: RatingContentModel?) {
        this.contentItem.value = contentItem
    }

    fun onCompleteButtonClick() {
        viewModelScope.launch(exceptionHandler) {
            if ((reviewText.value ?: "").isNotEmpty()) {
                recommendService.putReview(
                    contentItem.value?.contentId ?: 0,
                    CreateReviewRequest(reviewText.value ?: "")
                )
                _completeButtonClickEvent.value = Event(contentItem.value!!)
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
                val recommendService = RetrofitClient.recommendService
                return FixReviewViewModel(recommendService) as T
            }
        }
    }
}
