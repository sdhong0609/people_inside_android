package com.beside153.peopleinside.viewmodel.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.model.recommend.Pick10Model
import com.beside153.peopleinside.service.RecommendService
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.recommend.Pick10ViewPagerAdapter.Pick10ViewPagerModel
import kotlinx.coroutines.launch

class RecommendViewModel(private val recommendService: RecommendService) : ViewModel() {
    private val pick10List = MutableLiveData<List<Pick10Model>>()

    private val _viewPagerList = MutableLiveData<List<Pick10ViewPagerModel>>()
    val viewPagerList: LiveData<List<Pick10ViewPagerModel>> get() = _viewPagerList

    private val _pick10ItemClickEvent = MutableLiveData<Event<Pick10Model>>()
    val pick10ItemClickEvent: LiveData<Event<Pick10Model>> get() = _pick10ItemClickEvent

    fun loadPick10List() {
        // 로딩 및 ExceptionHandler 구현 필요

        viewModelScope.launch {
            pick10List.value = recommendService.getPick10List()

            _viewPagerList.value = viewPagerList()
        }
    }

    @Suppress("SpreadOperator")
    private fun viewPagerList(): List<Pick10ViewPagerModel> {
        return listOf(
            *pick10List.value?.map { Pick10ViewPagerModel.Pick10Item(it) }?.toTypedArray() ?: emptyArray(),
            Pick10ViewPagerModel.RefreshView
        )
    }

    fun onPick10ItemClick(item: Pick10Model) {
        _pick10ItemClickEvent.value = Event(item)
    }
}
