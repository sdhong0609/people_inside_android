package com.beside153.peopleinside.viewmodel.community

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.common.CreateContentRequest
import com.beside153.peopleinside.service.community.CommunityCommentService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixCommentViewModel @Inject constructor(
    private val communityCommentService: CommunityCommentService
) : BaseViewModel() {

    val commentText = MutableLiveData("")

    private val _isCompleteButtonEnabled = MutableLiveData(false)
    val isCompleteButtonEnabled: MutableLiveData<Boolean> get() = _isCompleteButtonEnabled

    private val _completeFixCommentEvent = MutableLiveData<Event<Unit>>()
    val completeFixCommentEvent: MutableLiveData<Event<Unit>> get() = _completeFixCommentEvent

    private var postId: Long = 0
    private var commentId: Long = 0

    fun initData(postId: Long, commentId: Long, content: String) {
        this.postId = postId
        this.commentId = commentId

        commentText.value = content
        checkCompleteButtonEnable()
    }

    fun onCommentTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        commentText.value = (s ?: "").toString()
        checkCompleteButtonEnable()
    }

    private fun checkCompleteButtonEnable() {
        _isCompleteButtonEnabled.value = commentText.value?.isNotEmpty() ?: false
    }

    fun onCompleteButtonClick() {
        if (_isCompleteButtonEnabled.value == false) return

        viewModelScope.launch(exceptionHandler) {
            communityCommentService.patchCommunityComment(
                postId,
                commentId,
                CreateContentRequest(commentText.value ?: "")
            )
            _completeFixCommentEvent.value = Event(Unit)
        }
    }
}
