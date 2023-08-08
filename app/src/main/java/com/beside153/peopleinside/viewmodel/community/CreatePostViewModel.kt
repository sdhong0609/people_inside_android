package com.beside153.peopleinside.viewmodel.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.community.MbtiTagModel
import com.beside153.peopleinside.util.Event

class CreatePostViewModel : BaseViewModel() {
    val postTitle = MutableLiveData("")
    val postContent = MutableLiveData("")

    private val _isCompleteButtonEnabled = MutableLiveData(false)
    val isCompleteButtonEnabled: LiveData<Boolean> get() = _isCompleteButtonEnabled

    private val _mbtiTagList = MutableLiveData<List<MbtiTagModel>>()
    val mbtiTagList: LiveData<List<MbtiTagModel>> get() = _mbtiTagList

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

    private var selectedMbtiCount = 0

    fun onTitleTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        postTitle.value = (s ?: "").toString()
        checkCompleteButtonEnable()
    }

    fun onContentTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        postContent.value = (s ?: "").toString()
        checkCompleteButtonEnable()
    }

    private fun checkCompleteButtonEnable() {
        _isCompleteButtonEnabled.value =
            postTitle.value?.isNotEmpty() == true && postContent.value?.isNotEmpty() == true
    }

    fun onCompleteButtonClick() {
        if (_isCompleteButtonEnabled.value == false) return
        _completeButtonClickEvent.value = Event(Unit)
    }

    fun initMbtiTagList() {
        _mbtiTagList.value = listOf(
            MbtiTagModel("INFP", false),
            MbtiTagModel("ENFP", false),
            MbtiTagModel("ESFJ", false),
            MbtiTagModel("ISFJ", false),
            MbtiTagModel("ISFP", false),
            MbtiTagModel("ESFP", false),
            MbtiTagModel("INTP", false),
            MbtiTagModel("INFJ", false),
            MbtiTagModel("ENFJ", false),
            MbtiTagModel("ENTP", false),
            MbtiTagModel("ESTJ", false),
            MbtiTagModel("ISTJ", false),
            MbtiTagModel("INTJ", false),
            MbtiTagModel("ISTP", false),
            MbtiTagModel("ESTP", false),
            MbtiTagModel("ENTJ", false)
        )
    }

    fun onMbtiTagItemClick(item: MbtiTagModel) {
        val updatedList = _mbtiTagList.value?.map {
            if (it == item) {
                if (it.isSelected) {
                    it.copy(isSelected = false)
                } else {
                    if (selectedMbtiCount >= 8) return
                    it.copy(isSelected = true)
                }
            } else {
                it
            }
        }

        selectedMbtiCount = updatedList?.count { it.isSelected } ?: 0
        _mbtiTagList.value = updatedList ?: emptyList()
    }
}
