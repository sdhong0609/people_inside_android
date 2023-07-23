package com.beside153.peopleinside.viewmodel.login.nonmember

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.common.MbtiModel
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.login.nonmember.NonMemberMbtiScreenAdapter.NonMemberMbtiScreenModel

class NonMemberMbtiChoiceViewModel : BaseViewModel() {
    private val _completeButtonClickEvent = MutableLiveData<Event<String>>()
    val completeButtonClickEvent: LiveData<Event<String>> get() = _completeButtonClickEvent

    private val _isCompleteButtonEnable = MutableLiveData(false)
    val isCompleteButtonEnable: LiveData<Boolean> get() = _isCompleteButtonEnable

    private val _screenList = MutableLiveData<List<NonMemberMbtiScreenModel>>()
    val screenList: LiveData<List<NonMemberMbtiScreenModel>> get() = _screenList

    private var mbtiList = mutableListOf<MbtiModel>()
    private var selectedMbtiItem: MbtiModel? = null

    fun initAllData() {
        mbtiList = mutableListOf(
            MbtiModel(R.drawable.mbti_large_img_infp, "INFP", false),
            MbtiModel(R.drawable.mbti_large_img_enfp, "ENFP", false),
            MbtiModel(R.drawable.mbti_large_img_esfj, "ESFJ", false),
            MbtiModel(R.drawable.mbti_large_img_isfj, "ISFJ", false),
            MbtiModel(R.drawable.mbti_large_img_isfp, "ISFP", false),
            MbtiModel(R.drawable.mbti_large_img_esfp, "ESFP", false),
            MbtiModel(R.drawable.mbti_large_img_intp, "INTP", false),
            MbtiModel(R.drawable.mbti_large_img_infj, "INFJ", false),
            MbtiModel(R.drawable.mbti_large_img_enfj, "ENFJ", false),
            MbtiModel(R.drawable.mbti_large_img_entp, "ENTP", false),
            MbtiModel(R.drawable.mbti_large_img_estj, "ESTJ", false),
            MbtiModel(R.drawable.mbti_large_img_istj, "ISTJ", false),
            MbtiModel(R.drawable.mbti_large_img_intj, "INTJ", false),
            MbtiModel(R.drawable.mbti_large_img_istp, "ISTP", false),
            MbtiModel(R.drawable.mbti_large_img_estp, "ESTP", false),
            MbtiModel(R.drawable.mbti_large_img_entj, "ENTJ", false)
        )

        _screenList.value = screenList()
    }

    fun onMbtiItemClick(item: MbtiModel) {
        val updatedList = mbtiList.map {
            if (it == item) {
                it.copy(isChosen = true)
            } else {
                it.copy(isChosen = false)
            }
        }

        mbtiList.clear()
        mbtiList.addAll(updatedList)
        _screenList.value = screenList()
        selectedMbtiItem = item
        _isCompleteButtonEnable.value = true
    }

    @Suppress("SpreadOperator")
    private fun screenList(): List<NonMemberMbtiScreenModel> {
        return listOf(
            NonMemberMbtiScreenModel.TitleViewItem,
            *mbtiList.map { NonMemberMbtiScreenModel.MbtiListItem(it) }.toTypedArray()
        )
    }

    fun onCompleteButtonClick() {
        _completeButtonClickEvent.value = Event(selectedMbtiItem?.mbtiText ?: "INFP")
    }
}
