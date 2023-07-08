package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.login.UserInfo
import com.beside153.peopleinside.service.MyContentService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.util.Event
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyPageViewModel(private val myContentService: MyContentService, private val userService: UserService) :
    BaseViewModel() {
    private val _bookmarkCount = MutableLiveData(0)
    val bookmarkCount: LiveData<Int> get() = _bookmarkCount

    private val _ratingCount = MutableLiveData(0)
    val ratingCount: LiveData<Int> get() = _ratingCount

    private val _bookmarkContentsClickEvent = MutableLiveData<Event<Unit>>()
    val bookmarkContentsClickEvent: LiveData<Event<Unit>> get() = _bookmarkContentsClickEvent

    private val _ratingContentsClickEvent = MutableLiveData<Event<Unit>>()
    val ratingContentsClickEvent: LiveData<Event<Unit>> get() = _ratingContentsClickEvent

    private val _editProfileClickEvent = MutableLiveData<Event<Unit>>()
    val editProfileClickEvent: LiveData<Event<Unit>> get() = _editProfileClickEvent

    private val _settingClickEvent = MutableLiveData<Event<Unit>>()
    val settingClickEvent: LiveData<Event<Unit>> get() = _settingClickEvent

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> get() = _userInfo

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            val bookmarkCountDeferred = async { myContentService.getBookmarkCount() }
            val ratingCountDeferred = async { myContentService.getRatingCount() }
            val userInfoDeffered = async { userService.getUserInfo(App.prefs.getUserId()) }

            _bookmarkCount.value = bookmarkCountDeferred.await()
            _ratingCount.value = ratingCountDeferred.await()
            _userInfo.value = userInfoDeffered.await()
        }
    }

    fun onBookmarkContentsClick() {
        _bookmarkContentsClickEvent.value = Event(Unit)
    }

    fun onRatingContentsClick() {
        _ratingContentsClickEvent.value = Event(Unit)
    }

    fun onEditProfileClick() {
        _editProfileClickEvent.value = Event(Unit)
    }

    fun onSettingClick() {
        _settingClickEvent.value = Event(Unit)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val myContentService = RetrofitClient.myContentService
                val userService = RetrofitClient.userService
                return MyPageViewModel(myContentService, userService) as T
            }
        }
    }
}
