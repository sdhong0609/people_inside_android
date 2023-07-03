package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.contentdetail.ContentRatingRequest
import com.beside153.peopleinside.model.mypage.Rating
import com.beside153.peopleinside.model.mypage.RatingContentModel
import com.beside153.peopleinside.service.ContentDetailService
import com.beside153.peopleinside.service.MyContentService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.roundToHalf
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RatingContentsViewModel(
    private val myContentService: MyContentService,
    private val contentDetailService: ContentDetailService
) : BaseViewModel() {
    private val _ratingCount = MutableLiveData(0)
    val ratingCount: LiveData<Int> get() = _ratingCount

    private val _contentList = MutableLiveData<List<RatingContentModel>>()
    val contentList: LiveData<List<RatingContentModel>> get() = _contentList

    private var page = 1

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            val ratingCountDeferred = async { myContentService.getRatingCount() }
            val contentListDeferred = async { myContentService.getRatingContents(page) }

            _ratingCount.value = ratingCountDeferred.await()
            _contentList.value = contentListDeferred.await()
        }
    }

    fun loadMoreData() {
        viewModelScope.launch(exceptionHandler) {
            val newContentList = myContentService.getRatingContents(++page)
            _contentList.value = _contentList.value?.plus(newContentList)
        }
    }

    fun onRatingChanged(rating: Float, item: RatingContentModel) {
        viewModelScope.launch(exceptionHandler) {
            val currentRating = item.rating.rating
            if (currentRating.roundToHalf() == rating) return@launch

            val currentRatingHasValue = 0 < currentRating && currentRating <= MAX_RATING
            if (currentRating <= 0) {
                contentDetailService.postContentRating(item.contentId, ContentRatingRequest(rating))
            } else if (currentRatingHasValue && (0 < rating && rating <= MAX_RATING)) {
                contentDetailService.putContentRating(item.contentId, ContentRatingRequest(rating))
            } else if (currentRatingHasValue && rating == 0f) {
                contentDetailService.deleteContentRating(item.contentId, item.rating.ratingId)
            }

            val updatedList = _contentList.value?.map {
                if (item == it) {
                    it.copy(rating = Rating(it.contentId, it.rating.ratingId, rating))
                } else {
                    it
                }
            }
            _contentList.value = updatedList ?: emptyList()
            _ratingCount.value = myContentService.getRatingCount()
        }
    }

    companion object {
        private const val MAX_RATING = 5

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val myContentService = RetrofitClient.myContentService
                val contentDetailService = RetrofitClient.contentDetailService
                return RatingContentsViewModel(myContentService, contentDetailService) as T
            }
        }
    }
}
