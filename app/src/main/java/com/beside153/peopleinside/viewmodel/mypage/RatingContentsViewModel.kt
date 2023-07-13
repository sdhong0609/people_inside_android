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
import com.beside153.peopleinside.model.mypage.Review
import com.beside153.peopleinside.service.ContentDetailService
import com.beside153.peopleinside.service.MyContentService
import com.beside153.peopleinside.service.RecommendService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.util.roundToHalf
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RatingContentsViewModel(
    private val myContentService: MyContentService,
    private val contentDetailService: ContentDetailService,
    private val recommendService: RecommendService
) : BaseViewModel() {
    private val _ratingCount = MutableLiveData(0)
    val ratingCount: LiveData<Int> get() = _ratingCount

    private val _contentList = MutableLiveData<List<RatingContentModel>>()
    val contentList: LiveData<List<RatingContentModel>> get() = _contentList

    private val _reviewFixClickEvent = MutableLiveData<Event<RatingContentModel>>()
    val reviewFixClickEvent: LiveData<Event<RatingContentModel>> get() = _reviewFixClickEvent

    private val _reviewDeleteClickEvent = MutableLiveData<Event<RatingContentModel>>()
    val reviewDeleteClickEvent: LiveData<Event<RatingContentModel>> get() = _reviewDeleteClickEvent

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
            val currentRating = item.rating?.rating ?: 0f
            if (currentRating.roundToHalf() == rating) return@launch

            val currentRatingHasValue = 0 < currentRating && currentRating <= MAX_RATING
            if (currentRating <= 0) {
                contentDetailService.postContentRating(item.contentId, ContentRatingRequest(rating))
            } else if (currentRatingHasValue && (0 < rating && rating <= MAX_RATING)) {
                contentDetailService.putContentRating(item.contentId, ContentRatingRequest(rating))
            } else if (currentRatingHasValue && rating == 0f) {
                contentDetailService.deleteContentRating(item.contentId, item.rating?.ratingId ?: 0)
            }

            val updatedList = _contentList.value?.map {
                if (item == it) {
                    it.copy(rating = Rating(it.contentId, it.rating?.ratingId ?: 0, rating))
                } else {
                    it
                }
            }
            _contentList.value = updatedList ?: emptyList()
            _ratingCount.value = myContentService.getRatingCount()
        }
    }

    fun onReviewFixClick(item: RatingContentModel) {
        _reviewFixClickEvent.value = Event(item)
    }

    fun updateFixedReview(fixedItem: RatingContentModel) {
        val updatedList = _contentList.value?.map {
            val review = fixedItem.review
            if (fixedItem.contentId == it.contentId) {
                it.copy(
                    contentId = fixedItem.contentId,
                    review = Review(
                        review?.contentId!!,
                        review.reviewId,
                        review.content,
                        review.likeCount,
                        review.writer
                    )
                )
            } else {
                it
            }
        }
        _contentList.value = updatedList ?: emptyList()
    }

    fun onReviewDeleteClick(item: RatingContentModel) {
        _reviewDeleteClickEvent.value = Event(item)
    }

    fun deleteReview(item: RatingContentModel) {
        viewModelScope.launch(exceptionHandler) {
            recommendService.deleteReview(item.contentId, item.review?.reviewId ?: 0)
        }
        val updatedList = _contentList.value?.map {
            if (item == it) {
                it.copy(review = null)
            } else {
                it
            }
        }

        _contentList.value = updatedList ?: emptyList()
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
                val recommendService = RetrofitClient.recommendService
                return RatingContentsViewModel(myContentService, contentDetailService, recommendService) as T
            }
        }
    }
}
