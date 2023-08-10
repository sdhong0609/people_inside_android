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

    private val _postDetailItem = MutableLiveData<CommunityPostModel>()
    val postDetailItem: LiveData<CommunityPostModel> get() = _postDetailItem

    private val _screenList = MutableLiveData<List<PostDetailScreenModel>>()
    val screenList: MutableLiveData<List<PostDetailScreenModel>> get() = _screenList

    private var postId = 1L

    fun setPostId(id: Long) {
        postId = id
    }

    fun initAllData() {
        viewModelScope.launch(exceptionHandler) {
            _postDetailItem.value = communityPostService.getCommunityPostDetail(postId)

            _screenList.value = screenList()
        }
    }

    private fun screenList(): List<PostDetailScreenModel> {
        return listOf(
            PostDetailScreenModel.PostItem(_postDetailItem.value!!)
        )
    }
}
