package com.beside153.peopleinside.viewmodel.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.service.community.CommunityPostService
import com.beside153.peopleinside.view.community.PostDetailScreenAdapter.PostDetailScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val communityPostService: CommunityPostService
) : BaseViewModel() {

    private var postDetailItem: CommunityPostModel? = null

    private val _screenList = MutableLiveData<List<PostDetailScreenModel>>()
    val screenList: MutableLiveData<List<PostDetailScreenModel>> get() = _screenList

    private val _uploadButtonVisible = MutableLiveData(false)
    val uploadButtonVisible: LiveData<Boolean> get() = _uploadButtonVisible

    private var postId = 1L

    fun setPostId(id: Long) {
        postId = id
    }

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            postDetailItem = communityPostService.getCommunityPostDetail(postId)

            _screenList.value = screenList()
        }
    }

    private fun screenList(): List<PostDetailScreenModel> {
        val commentAreaList = if ((postDetailItem?.totalComment ?: 0) <= 0) {
            listOf(PostDetailScreenModel.NoCommentView)
        } else {
            listOf(PostDetailScreenModel.NoCommentView)
        }

        return listOf(PostDetailScreenModel.PostItem(postDetailItem!!)) + commentAreaList
    }

    fun setUploadButtonVisible(isVisible: Boolean) {
        _uploadButtonVisible.value = isVisible
    }
}
