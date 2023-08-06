package com.beside153.peopleinside.viewmodel.community

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beside153.peopleinside.base.BaseViewModel

class CommunitySearchViewModel : BaseViewModel() {

    private val _keyword = MutableLiveData("")
    val keyword: LiveData<String> get() = _keyword

    fun afterKeywordTextChanged(editable: Editable?) {
        _keyword.value = editable.toString()
//        loadSearchingTitle()
    }

    fun onSearchCancelClick() {
        _keyword.value = ""
//        initSearchScreen()
    }
}
