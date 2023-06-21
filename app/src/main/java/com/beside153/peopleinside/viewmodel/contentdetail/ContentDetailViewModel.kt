package com.beside153.peopleinside.viewmodel.contentdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.model.contentdetail.ContentDetailModel
import com.beside153.peopleinside.service.ContentDetailService
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.launch

class ContentDetailViewModel(private val contentDetailService: ContentDetailService) : ViewModel() {
    private val _backButtonClickEvent = MutableLiveData<Event<Unit>>()
    val backButtonClickEvent: LiveData<Event<Unit>> get() = _backButtonClickEvent

    private val _contentDetailItem = MutableLiveData<ContentDetailModel>()
    val contentDetailItem: LiveData<ContentDetailModel> get() = _contentDetailItem

    fun onBackButtonClick() {
        _backButtonClickEvent.value = Event(Unit)
    }

    fun loadContentDetail(contentId: Int) {
        // 로딩 및 ExceptionHandler 구현 필요

        viewModelScope.launch {
            _contentDetailItem.value = contentDetailService.getContentDetail(contentId)
        }
    }
}
