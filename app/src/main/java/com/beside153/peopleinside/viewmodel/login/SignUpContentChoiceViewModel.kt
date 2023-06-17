package com.beside153.peopleinside.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beside153.peopleinside.model.login.ContentModel
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.login.ContentScreenAdapter.ContentScreenModel

class SignUpContentChoiceViewModel : ViewModel() {
    private val _contentList = MutableLiveData<MutableList<ContentModel>>()
    val contentList: LiveData<MutableList<ContentModel>> get() = _contentList

    private val _contentItemClickEvent = MutableLiveData<Event<Unit>>()
    val contentItemClickEvent: LiveData<Event<Unit>> get() = _contentItemClickEvent

    private val _choiceCount = MutableLiveData(0)
    val choiceCount: LiveData<Int> get() = _choiceCount

    private val _isCompleteButtonEnable = MutableLiveData(false)
    val isCompleteButtonEnable: LiveData<Boolean> get() = _isCompleteButtonEnable

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

    private val _backButtonClickEvent = MutableLiveData<Event<Unit>>()
    val backButtonClickEvent: LiveData<Event<Unit>> get() = _backButtonClickEvent

    fun onContentItemClick(item: ContentModel) {
        val updatedList = _contentList.value?.map {
            if (it == item) {
                if (!it.isChosen) {
                    _choiceCount.value = _choiceCount.value?.plus(1)
                    it.copy(isChosen = true)
                } else {
                    _choiceCount.value = _choiceCount.value?.minus(1)
                    it.copy(isChosen = false)
                }
            } else {
                it
            }
        }

        _contentList.value?.clear()
        _contentList.value?.addAll(updatedList ?: mutableListOf())
        _contentItemClickEvent.value = Event(Unit)
    }

    @Suppress("SpreadOperator")
    fun screenList(): List<ContentScreenModel> {
        return listOf(
            ContentScreenModel.TitleViewItem,
            *_contentList.value?.map { ContentScreenModel.ContentListItem(it) }?.toTypedArray() ?: arrayOf()
        )
    }

    fun initContentList() {
        _contentList.value = mutableListOf(
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/ej7Br2B8dkZZBGa6vDE8HqATgU7.jpg",
                "블랙 미러",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2ts8XDLOTndAeb1Z7xdNoJX2PJG.jpg",
                "블랙 클로버: 마법제의 검",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lCUvpSvjAPU82HvJ8XfR74Chv5r.jpg",
                "그레이 아나토미",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9WF6TxCYwdiZw51NM92ConaQz1w.jpg",
                "존 윅 4",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wXNihLltMCGR7XepN39syIlCt5X.jpg",
                "분노의 질주: 라이드 오어 다이",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lCanGgsqF4xD2WA5NF8PWeT3IXd.jpg",
                "칸다하르",
                false
            )
        )
    }

    fun onBackButtonClick() {
        _backButtonClickEvent.value = Event(Unit)
    }
}
