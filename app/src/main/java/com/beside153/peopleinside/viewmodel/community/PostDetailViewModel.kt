package com.beside153.peopleinside.viewmodel.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.community.comment.CommunityCommentModel
import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.service.community.CommunityCommentService
import com.beside153.peopleinside.service.community.CommunityPostService
import com.beside153.peopleinside.view.community.PostDetailScreenAdapter.PostDetailScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val communityPostService: CommunityPostService,
    private val communityCommentService: CommunityCommentService
) : BaseViewModel() {

    private val _screenList = MutableLiveData<List<PostDetailScreenModel>>()
    val screenList: MutableLiveData<List<PostDetailScreenModel>> get() = _screenList

    private val _uploadButtonVisible = MutableLiveData(false)
    val uploadButtonVisible: LiveData<Boolean> get() = _uploadButtonVisible

    private var postId = 1L
    private var postDetailItem: CommunityPostModel? = null
    private var commentList = listOf<CommunityCommentModel>()
    private var page = 1

    fun setPostId(id: Long) {
        postId = id
    }

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            val postDetailItemDeferred = async { communityPostService.getCommunityPostDetail(postId) }
            val commentListDeferred = async { communityCommentService.getCommunityCommentList(postId, page) }
            postDetailItem = postDetailItemDeferred.await()
            commentList = commentListDeferred.await()

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

    fun setUploadButtonVisible(isVisible: Boolean) {
        _uploadButtonVisible.value = isVisible
    }
}
