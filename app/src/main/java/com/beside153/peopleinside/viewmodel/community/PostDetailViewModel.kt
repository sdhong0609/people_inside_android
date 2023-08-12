package com.beside153.peopleinside.viewmodel.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.common.CreateContentRequest
import com.beside153.peopleinside.model.community.comment.CommunityCommentModel
import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.service.community.CommunityCommentService
import com.beside153.peopleinside.service.community.CommunityPostService
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.community.PostDetailScreenAdapter.PostDetailScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

interface PostDetailViewModelHandler {
    val postMbtiList: List<String>
    fun onCommentDotsClick(item: CommunityCommentModel)
}

data class CommentFixModel(
    val postId: Long,
    val commentId: Long,
    val commentContent: String
)

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val communityPostService: CommunityPostService,
    private val communityCommentService: CommunityCommentService
) : BaseViewModel(), PostDetailViewModelHandler {

    val commentText = MutableLiveData("")

    private val _screenList = MutableLiveData<List<PostDetailScreenModel>>()
    val screenList: MutableLiveData<List<PostDetailScreenModel>> get() = _screenList

    private val _uploadButtonVisible = MutableLiveData(false)
    val uploadButtonVisible: LiveData<Boolean> get() = _uploadButtonVisible

    private val _isUploadCommentEnabled = MutableLiveData(false)
    val isUploadCommentEnabled: LiveData<Boolean> get() = _isUploadCommentEnabled

    private val _completeUploadCommentEvent = MutableLiveData<Event<Unit>>()
    val completeUploadCommentEvent: LiveData<Event<Unit>> get() = _completeUploadCommentEvent

    private val _postDotsClickEvent = MutableLiveData<Event<Boolean>>()
    val postDotsClickEvent: LiveData<Event<Boolean>> get() = _postDotsClickEvent

    private val _commentDotsClickEvent = MutableLiveData<Event<Boolean>>()
    val commentDotsClickEvent: LiveData<Event<Boolean>> get() = _commentDotsClickEvent

    private val _commentFixClickEvent = MutableLiveData<Event<CommentFixModel>>()
    val commentFixClickEvent: LiveData<Event<CommentFixModel>> get() = _commentFixClickEvent

    private val _completeDeletePostEvent = MutableLiveData<Event<Unit>>()
    val completeDeletePostEvent: LiveData<Event<Unit>> get() = _completeDeletePostEvent

    private val _completeDeleteCommentEvent = MutableLiveData<Event<Unit>>()
    val completeDeleteCommentEvent: LiveData<Event<Unit>> get() = _completeDeleteCommentEvent

    private val _completeReportEvent = MutableLiveData<Event<Unit>>()
    val completeReportEvent: LiveData<Event<Unit>> get() = _completeReportEvent

    private val _goToNonMemberLoginEvent = MutableLiveData<Event<Unit>>()
    val goToNonMemberLoginEvent: LiveData<Event<Unit>> get() = _goToNonMemberLoginEvent

    private var postId = 1L
    private var postDetailItem: CommunityPostModel? = null
    private var commentList = listOf<CommunityCommentModel>()
    private var page = 1
    private var selectedCommentId = 0L
    private var selectedCommentContent = ""

    override var postMbtiList = listOf<String>()

    fun setPostId(id: Long) {
        postId = id
    }

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            postDetailItem = communityPostService.getCommunityPostDetail(postId)
            commentList = listOf()
            (1..page).forEach {
                val newCommentList = communityCommentService.getCommunityCommentList(postId, it)
                commentList = commentList.plus(newCommentList)
            }
            postMbtiList = postDetailItem?.mbtiList ?: listOf()
            _screenList.value = screenList()
        }
    }

    fun loadMoreCommentList() {
        viewModelScope.launch(exceptionHandler) {
            val newCommentList = communityCommentService.getCommunityCommentList(postId, ++page)
            commentList = commentList.plus(newCommentList)
            _screenList.value = screenList()
        }
    }

    private fun screenList(): List<PostDetailScreenModel> {
        val commentAreaList = if ((postDetailItem?.totalComment ?: 0) <= 0) {
            listOf(PostDetailScreenModel.NoCommentView)
        } else {
            listOf(*commentList.map { PostDetailScreenModel.CommentItem(it) }.toTypedArray())
        }

        return listOf(PostDetailScreenModel.PostItem(postDetailItem!!)) + commentAreaList
    }

    fun onCommentTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        commentText.value = (s ?: "").toString()
        checkUploadCommentEnable()
    }

    private fun checkUploadCommentEnable() {
        _isUploadCommentEnabled.value = commentText.value?.isNotEmpty() ?: false
    }

    fun setUploadButtonVisible(isVisible: Boolean) {
        _uploadButtonVisible.value = isVisible
    }

    fun onUploadCommentButtonClick() {
        viewModelScope.launch(exceptionHandler) {
            communityCommentService.postCommunityComment(postId, CreateContentRequest(commentText.value ?: ""))
            commentText.value = ""
            _completeUploadCommentEvent.value = Event(Unit)
        }
    }

    fun onPostVerticalDotsClick() {
        if (!App.prefs.getIsMember()) {
            _goToNonMemberLoginEvent.value = Event(Unit)
            return
        }
        if (App.prefs.getUserId().toLong() == (postDetailItem?.author?.userId ?: 1L)) {
            _postDotsClickEvent.value = Event(true)
            return
        }
        _postDotsClickEvent.value = Event(false)
    }

    override fun onCommentDotsClick(item: CommunityCommentModel) {
        if (!App.prefs.getIsMember()) {
            _goToNonMemberLoginEvent.value = Event(Unit)
            return
        }

        selectedCommentId = item.commentId
        selectedCommentContent = item.content
        if (App.prefs.getUserId().toLong() == item.author.userId) {
            _commentDotsClickEvent.value = Event(true)
            return
        }
        _commentDotsClickEvent.value = Event(false)
    }

    fun onCommentFixClick() {
        _commentFixClickEvent.value = Event(CommentFixModel(postId, selectedCommentId, selectedCommentContent))
    }

    fun deletePost() {
        viewModelScope.launch(exceptionHandler) {
            communityPostService.deleteCommunityPost(postId)
            _completeDeletePostEvent.value = Event(Unit)
        }
    }

    fun deleteComment() {
        viewModelScope.launch(exceptionHandler) {
            communityCommentService.deleteCommunityComment(postId, selectedCommentId)
            _completeDeleteCommentEvent.value = Event(Unit)
            this@PostDetailViewModel.initAllData()
        }
    }

    fun reportPost(reportId: Int) {
        viewModelScope.launch(exceptionHandler) {
            communityPostService.postCommunityPostReport(postId, reportId)
            _completeReportEvent.value = Event(Unit)
        }
    }

    fun reportComment(reportId: Int) {
        viewModelScope.launch(exceptionHandler) {
            communityCommentService.postCommunityCommentReport(postId, selectedCommentId, reportId)
            _completeReportEvent.value = Event(Unit)
        }
    }
}
