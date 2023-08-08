package com.beside153.peopleinside.viewmodel.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.community.MbtiTagModel

class CreatePostViewModel : BaseViewModel() {
    private val _mbtiTagList = MutableLiveData<List<MbtiTagModel>>()
    val mbtiTagList: LiveData<List<MbtiTagModel>> get() = _mbtiTagList

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
                    it.copy(isSelected = true)
                }
            } else {
                it
            }
        }

        _mbtiTagList.value = updatedList ?: emptyList()
    }
}
