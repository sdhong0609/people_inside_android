package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.service.MyContentService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyPageViewModel(private val myContentService: MyContentService) : BaseViewModel() {
    private val _bookmarkCount = MutableLiveData(0)
    val bookmarkCount: LiveData<Int> get() = _bookmarkCount

    private val _ratingCount = MutableLiveData(0)
    val ratingCount: LiveData<Int> get() = _ratingCount

    private val _bookmarkContentsClickEvent = MutableLiveData<Event<Unit>>()
    val bookmarkContentsClickEvent: LiveData<Event<Unit>> get() = _bookmarkContentsClickEvent

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            val bookmarkCountDeferred = async { myContentService.getBookmarkCount() }
            val ratingCountDeferred = async { myContentService.getRatingCount() }

            _bookmarkCount.value = bookmarkCountDeferred.await()
            _ratingCount.value = ratingCountDeferred.await()
        }
    }

    fun onBookmarkContentsClick() {
        _bookmarkContentsClickEvent.value = Event(Unit)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val myContentService = RetrofitClient.myContentService
                return MyPageViewModel(myContentService) as T
            }
        }
    }
}
