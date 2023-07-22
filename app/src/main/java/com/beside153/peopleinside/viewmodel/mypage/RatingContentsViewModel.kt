package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.rating.ContentRatingRequest
import com.beside153.peopleinside.model.mycontent.Rating
import com.beside153.peopleinside.model.mycontent.RatedContentModel
import com.beside153.peopleinside.model.mycontent.Review
import com.beside153.peopleinside.service.MyContentService
import com.beside153.peopleinside.service.RatingService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.ReviewService
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.util.roundToHalf
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RatingContentsViewModel(
    private val myContentService: MyContentService,
    private val ratingService: RatingService,
    private val reviewService: ReviewService
) : BaseViewModel() {
    private val _ratingCount = MutableLiveData(0)
    val ratingCount: LiveData<Int> get() = _ratingCount

    private val _contentList = MutableLiveData<List<RatedContentModel>>()
    val contentList: LiveData<List<RatedContentModel>> get() = _contentList

    private val _reviewFixClickEvent = MutableLiveData<Event<RatedContentModel>>()
    val reviewFixClickEvent: LiveData<Event<RatedContentModel>> get() = _reviewFixClickEvent

    private val _reviewDeleteClickEvent = MutableLiveData<Event<RatedContentModel>>()
    val reviewDeleteClickEvent: LiveData<Event<RatedContentModel>> get() = _reviewDeleteClickEvent

    private var page = 1

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            val ratingCountDeferred = async { myContentService.getRatedCount() }
            val contentListDeferred = async { myContentService.getRatedContents(page) }

            _ratingCount.value = ratingCountDeferred.await()
            _contentList.value = contentListDeferred.await()
        }
    }

    fun loadMoreData() {
        viewModelScope.launch(exceptionHandler) {
            val newContentList = myContentService.getRatedContents(++page)
            _contentList.value = _contentList.value?.plus(newContentList)
        }
    }

    fun onRatingChanged(rating: Float, item: RatedContentModel) {
        viewModelScope.launch(exceptionHandler) {
            val currentRating = item.rating?.rating ?: 0f
            if (currentRating.roundToHalf() == rating) return@launch

            val currentRatingHasValue = 0 < currentRating && currentRating <= MAX_RATING
            if (currentRating <= 0) {
                ratingService.postContentRating(item.contentId, ContentRatingRequest(rating))
            } else if (currentRatingHasValue && (0 < rating && rating <= MAX_RATING)) {
                ratingService.putContentRating(item.contentId, ContentRatingRequest(rating))
            } else if (currentRatingHasValue && rating == 0f) {
                ratingService.deleteContentRating(item.contentId, item.rating?.ratingId ?: 0)
            }

            val updatedList = _contentList.value?.map {
                if (item == it) {
                    it.copy(rating = Rating(it.contentId, it.rating?.ratingId ?: 0, rating))
                } else {
                    it
                }
            }
            _contentList.value = updatedList ?: emptyList()
            _ratingCount.value = myContentService.getRatedCount()
        }
    }

    fun onReviewFixClick(item: RatedContentModel) {
        _reviewFixClickEvent.value = Event(item)
    }

    fun updateFixedReview(fixedItem: RatedContentModel) {
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

    fun onReviewDeleteClick(item: RatedContentModel) {
        _reviewDeleteClickEvent.value = Event(item)
    }

    fun deleteReview(item: RatedContentModel) {
        viewModelScope.launch(exceptionHandler) {
            reviewService.deleteReview(item.contentId, item.review?.reviewId ?: 0)
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
                val ratingService = RetrofitClient.ratingService
                val reviewService = RetrofitClient.reviewService
                return RatingContentsViewModel(myContentService, ratingService, reviewService) as T
            }
        }
    }
}
