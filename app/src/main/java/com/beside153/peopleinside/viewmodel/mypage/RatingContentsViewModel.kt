package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.mypage.RatingContentModel
import com.beside153.peopleinside.service.MyContentService
import com.beside153.peopleinside.service.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RatingContentsViewModel(private val myContentService: MyContentService) : BaseViewModel() {
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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val myContentService = RetrofitClient.myContentService
                return RatingContentsViewModel(myContentService) as T
            }
        }
    }
}
