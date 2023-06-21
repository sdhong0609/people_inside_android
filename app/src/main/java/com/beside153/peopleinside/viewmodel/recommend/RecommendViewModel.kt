package com.beside153.peopleinside.viewmodel.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.model.recommend.Pick10Model
import com.beside153.peopleinside.service.RecommendService
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

class RecommendViewModel(private val recommendService: RecommendService) : ViewModel() {
    private val _pick10List = MutableLiveData<List<Pick10Model>>()
    val pick10List: LiveData<List<Pick10Model>> get() = _pick10List

    private val _pick10ItemClickEvent = MutableLiveData<Event<Pick10Model>>()
    val pick10ItemClickEvent: LiveData<Event<Pick10Model>> get() = _pick10ItemClickEvent

    fun loadPick10List() {
        // 로딩 및 ExceptionHandler 구현 필요

        viewModelScope.launch {
            _pick10List.value = recommendService.getPick10List()
        }
    }

    fun onPick10ItemClick(item: Pick10Model) {
        _pick10ItemClickEvent.value = Event(item)
    }
}
