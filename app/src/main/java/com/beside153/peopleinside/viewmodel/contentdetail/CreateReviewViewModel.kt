package com.beside153.peopleinside.viewmodel.contentdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.model.contentdetail.CreateReviewRequest
import com.beside153.peopleinside.service.RecommendService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class CreateReviewViewModel(private val recommendService: RecommendService) : BaseViewModel() {

    val reviewText = MutableLiveData("")

    private val contentId = MutableLiveData(0)

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

    fun setContentId(id: Int) {
        contentId.value = id
    }

    fun setContent(content: String) {
        reviewText.value = content
    }

    fun onCompleteButtonClick() {
        // exceptionHandler 구현 필요

        viewModelScope.launch {
            if ((reviewText.value ?: "").isNotEmpty()) {
                recommendService.postReview(contentId.value ?: 0, CreateReviewRequest(reviewText.value ?: ""))
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
                val recommendService = RetrofitClient.recommendService
                return CreateReviewViewModel(recommendService) as T
            }
        }
    }
}
