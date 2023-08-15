package com.beside153.peopleinside.viewmodel.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beside153.peopleinside.base.BaseViewModel
import com.beside153.peopleinside.common.exception.ApiException
import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.model.community.post.CommunityPostRequest
import com.beside153.peopleinside.model.community.post.Mbti
import com.beside153.peopleinside.model.community.post.MbtiTagModel
import com.beside153.peopleinside.service.community.CommunityPostService
import com.beside153.peopleinside.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PostMode {
    CREATE, FIX
}

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val communityPostService: CommunityPostService
) : BaseViewModel() {
    val postTitle = MutableLiveData("")
    val postContent = MutableLiveData("")

    private val _isCompleteButtonEnabled = MutableLiveData(false)
    val isCompleteButtonEnabled: LiveData<Boolean> get() = _isCompleteButtonEnabled

    private val _mbtiTagList = MutableLiveData<List<MbtiTagModel>>()
    val mbtiTagList: LiveData<List<MbtiTagModel>> get() = _mbtiTagList

    private val _completePostEvent = MutableLiveData<Event<PostMode>>()
    val completePostEvent: LiveData<Event<PostMode>> get() = _completePostEvent

    private val _isFixPost = MutableLiveData(false)
    val isFixPost: LiveData<Boolean> get() = _isFixPost

    private val _showBadWordDialogEvent = MutableLiveData<Event<Unit>>()
    val showBadWordDialogEvent: LiveData<Event<Unit>> get() = _showBadWordDialogEvent

    private var selectedMbtiList = mutableListOf<String>()
    private var mbtiRequest = Mbti()
    private var postItem: CommunityPostModel? = null
    private var postId = 0L

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
                    selectedMbtiList.remove(it.mbtiTag)
                    it.copy(isSelected = false)
                } else {
                    selectedMbtiList.add(it.mbtiTag)
                    it.copy(isSelected = true)
                }
            } else {
                it
            }
        }

        _mbtiTagList.value = updatedList ?: emptyList()
    }

    fun initPost(id: Long) {
        viewModelScope.launch(exceptionHandler) {
            postId = id
            _isFixPost.value = true
            postItem = communityPostService.getCommunityPostDetail(postId)
            postTitle.value = postItem?.title ?: ""
            postContent.value = postItem?.content ?: ""
            val mbtiList = postItem?.mbtiList ?: listOf()
            for (mbti in mbtiList) {
                onMbtiTagItemClick(MbtiTagModel(mbti.uppercase(), false))
            }
        }
    }

    fun onCompleteButtonClick() {
        if (_isCompleteButtonEnabled.value == false) return

        val ceh = CoroutineExceptionHandler { context, t ->
            when (t) {
                is ApiException -> {
                    if (t.error.statusCode == 403) {
                        _showBadWordDialogEvent.value = Event(Unit)
                    } else {
                        exceptionHandler.handleException(context, t)
                    }
                }

                else -> exceptionHandler.handleException(context, t)
            }
        }

        viewModelScope.launch(ceh) {
            for (selectedMbti in selectedMbtiList) {
                when (selectedMbti) {
                    "INFP" -> mbtiRequest = mbtiRequest.copy(infp = true)
                    "ENFP" -> mbtiRequest = mbtiRequest.copy(enfp = true)
                    "ESFJ" -> mbtiRequest = mbtiRequest.copy(esfj = true)
                    "ISFJ" -> mbtiRequest = mbtiRequest.copy(isfj = true)
                    "ISFP" -> mbtiRequest = mbtiRequest.copy(isfp = true)
                    "ESFP" -> mbtiRequest = mbtiRequest.copy(esfp = true)
                    "INTP" -> mbtiRequest = mbtiRequest.copy(intp = true)
                    "INFJ" -> mbtiRequest = mbtiRequest.copy(infj = true)
                    "ENFJ" -> mbtiRequest = mbtiRequest.copy(enfj = true)
                    "ENTP" -> mbtiRequest = mbtiRequest.copy(entp = true)
                    "ESTJ" -> mbtiRequest = mbtiRequest.copy(estj = true)
                    "ISTJ" -> mbtiRequest = mbtiRequest.copy(istj = true)
                    "INTJ" -> mbtiRequest = mbtiRequest.copy(intj = true)
                    "ISTP" -> mbtiRequest = mbtiRequest.copy(istp = true)
                    "ESTP" -> mbtiRequest = mbtiRequest.copy(estp = true)
                    "ENTJ" -> mbtiRequest = mbtiRequest.copy(entj = true)
                }
            }
            if (_isFixPost.value == true) {
                communityPostService.patchCommunityPost(
                    postId,
                    CommunityPostRequest(
                        postTitle.value ?: "",
                        postContent.value ?: "",
                        mbtiRequest
                    )
                )
                _completePostEvent.value = Event(PostMode.FIX)
                return@launch
            }
            communityPostService.postCommunityPost(
                CommunityPostRequest(
                    postTitle.value ?: "",
                    postContent.value ?: "",
                    mbtiRequest
                )
            )
            _completePostEvent.value = Event(PostMode.CREATE)
        }
    }
}
