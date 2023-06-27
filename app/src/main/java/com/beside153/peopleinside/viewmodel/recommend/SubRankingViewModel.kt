package com.beside153.peopleinside.viewmodel.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.model.recommend.SubRankingModel
import com.beside153.peopleinside.service.RecommendService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class SubRankingViewModel(private val recommendService: RecommendService) : BaseViewModel() {

    private val _subRankingList = MutableLiveData<List<SubRankingModel>>()
    val subRankingList: LiveData<List<SubRankingModel>> get() = _subRankingList

    private val _subRankingItemClickEvent = MutableLiveData<Event<SubRankingModel>>()
    val subRankingItemClickEvent: LiveData<Event<SubRankingModel>> get() = _subRankingItemClickEvent

    fun initData(mediaType: String) {
        // exceptionHandler 구현 필요

        viewModelScope.launch {
            _subRankingList.value = recommendService.getSubRankingItem(mediaType, MAX_TAKE)

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
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val recommendService = RetrofitClient.recommendService
                return SubRankingViewModel(recommendService) as T
            }
        }

        private const val MAX_TAKE = 20
    }
}
