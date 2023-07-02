package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.mypage.BookmarkedContentModel
import com.beside153.peopleinside.service.MyContentService
import com.beside153.peopleinside.service.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BookmarkedContentsViewModel(private val myContentService: MyContentService) : BaseViewModel() {
    private val _bookmarkCount = MutableLiveData(0)
    val bookmarkCount: LiveData<Int> get() = _bookmarkCount

    private val _contentList = MutableLiveData<List<BookmarkedContentModel>>()
    val contentList: LiveData<List<BookmarkedContentModel>> get() = _contentList

    var page = 1

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            val bookmarkCountDeferred = async { myContentService.getBookmarkCount() }
            val contentListDeferred = async { myContentService.getBookmarkedContents(page) }

            _bookmarkCount.value = bookmarkCountDeferred.await()
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
                return BookmarkedContentsViewModel(myContentService) as T
            }
        }
    }
}
