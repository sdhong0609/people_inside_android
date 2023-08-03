package com.beside153.peopleinside.viewmodel.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.user.UserInfo
import com.beside153.peopleinside.service.MyContentService
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myContentService: MyContentService,
    private val userService: UserService
) :
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
            val bookmarkCountDeferred = async { myContentService.getBookmarkedCount() }
            val ratingCountDeferred = async { myContentService.getRatedCount() }
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
}
