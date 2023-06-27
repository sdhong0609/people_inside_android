package com.beside153.peopleinside.viewmodel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beside153.peopleinside.model.login.ContentModel
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.login.ContentScreenAdapter.ContentScreenModel
import com.beside153.peopleinside.viewmodel.BaseViewModel

class SignUpContentChoiceViewModel : BaseViewModel() {
    private var contentList = mutableListOf<ContentModel>()

    private val _contentItemClickEvent = MutableLiveData<Event<Unit>>()
    val contentItemClickEvent: LiveData<Event<Unit>> get() = _contentItemClickEvent

    private val _choiceCount = MutableLiveData(0)
    val choiceCount: LiveData<Int> get() = _choiceCount

    private val _isCompleteButtonEnable = MutableLiveData(false)
    val isCompleteButtonEnable: LiveData<Boolean> get() = _isCompleteButtonEnable

    private val _completeButtonClickEvent = MutableLiveData<Event<Unit>>()
    val completeButtonClickEvent: LiveData<Event<Unit>> get() = _completeButtonClickEvent

    fun onContentItemClick(item: ContentModel) {
        val updatedList = contentList.map {
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

        contentList.clear()
        contentList.addAll(updatedList)
        _contentItemClickEvent.value = Event(Unit)
        checkCompleteButtonEnable()
    }

    @Suppress("SpreadOperator")
    fun screenList(): List<ContentScreenModel> {
        return listOf(
            ContentScreenModel.TitleViewItem,
            *contentList.map { ContentScreenModel.ContentListItem(it) }.toTypedArray()
        )
    }

    private fun checkCompleteButtonEnable() {
        _isCompleteButtonEnable.value = (_choiceCount.value ?: 0) >= MAX_CHOICE_COUNT
    }

    fun onCompleteButtonClick() {
        _completeButtonClickEvent.value = Event(Unit)
    }

    fun initContentList() {
        contentList = mutableListOf(
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

    companion object {
        private const val MAX_CHOICE_COUNT = 5
    }
}
