package com.beside153.peopleinside.viewmodel.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.mediacontent.OnBoardingChosenContentModel
import com.beside153.peopleinside.model.mediacontent.OnBoardingContentModel
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.service.mediacontent.MediaContentService
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.onboarding.ContentScreenAdapter.ContentScreenModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ContentChoiceViewModel(
    private val mediaContentService: MediaContentService,
    private val userService: UserService
) : BaseViewModel() {

    private val _choiceCount = MutableLiveData(0)
    val choiceCount: LiveData<Int> get() = _choiceCount

    private val _isCompleteButtonEnable = MutableLiveData(false)
    val isCompleteButtonEnable: LiveData<Boolean> get() = _isCompleteButtonEnable

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

    private val _screenList = MutableLiveData<List<ContentScreenModel>>()
    val screenList: LiveData<List<ContentScreenModel>> get() = _screenList

    private var page = 1
    private var contentList = listOf<OnBoardingContentModel>()

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            contentList = mediaContentService.getOnBoardingContents(page)
        }
        _screenList.value = screenList()
    }

    fun loadMoreData() {
        viewModelScope.launch(exceptionHandler) {
            val newContentList = mediaContentService.getOnBoardingContents(++page)
            contentList = contentList.plus(newContentList)

            _screenList.value = screenList()
        }
    }

    fun onContentItemClick(item: OnBoardingContentModel) {
        val updatedList = contentList.map {
            if (it == item) {
                if (!it.isChosen) {
                    _choiceCount.value = _choiceCount.value?.plus(1)
                    it.copy(isChosen = true)
                } else {
                    _choiceCount.value = _choiceCount.value?.minus(1)
                    it.copy(isChosen = false)
                }
            } else {
                it
            }
        }

        contentList = updatedList
        checkCompleteButtonEnable()
        _screenList.value = screenList()
    }

    @Suppress("SpreadOperator")
    private fun screenList(): List<ContentScreenModel> {
        return listOf(
            ContentScreenModel.TitleViewItem,
            *contentList.map { ContentScreenModel.ContentListItem(it) }.toTypedArray()
        )
    }

    private fun checkCompleteButtonEnable() {
        _isCompleteButtonEnable.value = (_choiceCount.value ?: 0) >= MAX_CHOICE_COUNT
    }

    fun onCompleteButtonClick() {
        viewModelScope.launch(exceptionHandler) {
            val chosenList: List<OnBoardingChosenContentModel> =
                contentList.filter { it.isChosen }
                    .map { OnBoardingChosenContentModel(it.contentId, MAX_RATING) }

            val chosenContentsDeferred = async { mediaContentService.postChosenContents(chosenList) }
            val onBoardingCompletedDeferred = async { userService.postOnBoardingCompleted(App.prefs.getUserId()) }

            val isSuccess = chosenContentsDeferred.await()
            onBoardingCompletedDeferred.await()

            if (isSuccess) {
                _completeButtonClickEvent.value = Event(Unit)
            }
        }
    }

    companion object {
        private const val MAX_CHOICE_COUNT = 5
        private const val MAX_RATING = 5f

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val mediaContentService = RetrofitClient.mediaContentService
                val userService = RetrofitClient.userService
                return ContentChoiceViewModel(mediaContentService, userService) as T
            }
        }
    }
}
