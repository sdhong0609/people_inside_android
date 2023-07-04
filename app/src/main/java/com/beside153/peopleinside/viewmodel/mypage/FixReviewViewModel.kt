package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.service.RecommendService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

class FixReviewViewModel(private val recommendService: RecommendService) : BaseViewModel() {
    val reviewText = MutableLiveData("")

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

//    fun setContent(content: String) {
//        reviewText.value = content
//    }

    fun onCompleteButtonClick() {
        viewModelScope.launch(exceptionHandler) {
            if ((reviewText.value ?: "").isNotEmpty()) {
//                recommendService.putReview(contentId, CreateReviewRequest(reviewText.value ?: ""))
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
                return FixReviewViewModel(recommendService) as T
            }
        }
    }
}
