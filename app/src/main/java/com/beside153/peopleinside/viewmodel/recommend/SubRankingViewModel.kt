package com.beside153.peopleinside.viewmodel.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.mediacontent.SubRankingModel
import com.beside153.peopleinside.service.mediacontent.MediaContentService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubRankingViewModel @Inject constructor(
    private val mediaContentService: MediaContentService
) : BaseViewModel() {

    private val _subRankingList = MutableLiveData<List<SubRankingModel>>()
    val subRankingList: LiveData<List<SubRankingModel>> get() = _subRankingList

    private val _subRankingItemClickEvent = MutableLiveData<Event<SubRankingModel>>()
    val subRankingItemClickEvent: LiveData<Event<SubRankingModel>> get() = _subRankingItemClickEvent

    fun initData(mediaType: String) {
        viewModelScope.launch(exceptionHandler) {
            _subRankingList.value = mediaContentService.getSubRankingItem(mediaType, MAX_TAKE)

            val updatedSubRankingList = _subRankingList.value?.mapIndexed { index, item ->
                item.copy(rank = index + 1)
            }
            _subRankingList.value = updatedSubRankingList ?: emptyList()
        }
    }

    fun onSubRankingItemClick(item: SubRankingModel) {
        _subRankingItemClickEvent.value = Event(item)
    }

    companion object {
        private const val MAX_TAKE = 20
    }
}
