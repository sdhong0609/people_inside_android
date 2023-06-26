package com.beside153.peopleinside.viewmodel.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.beside153.peopleinside.model.login.UserInfo
import com.beside153.peopleinside.model.recommend.Pick10Model
import com.beside153.peopleinside.model.recommend.RatingBattleModel
import com.beside153.peopleinside.model.recommend.SubRankingModel
import com.beside153.peopleinside.service.BookmarkService
import com.beside153.peopleinside.service.RecommendService
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.service.UserService
import com.beside153.peopleinside.util.Event
import com.beside153.peopleinside.view.App
import com.beside153.peopleinside.view.recommend.Pick10ViewPagerAdapter.Pick10ViewPagerModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RecommendViewModel(
    private val userService: UserService,
    private val recommendService: RecommendService,
    private val bookmarkService: BookmarkService
) : ViewModel() {

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> get() = _userInfo

    private val _progressBarVisible = MutableLiveData(true)
    val progressBarVisible: LiveData<Boolean> get() = _progressBarVisible

    private val pick10List = MutableLiveData<List<Pick10Model>>()

    private val _viewPagerList = MutableLiveData<List<Pick10ViewPagerModel>>()
    val viewPagerList: LiveData<List<Pick10ViewPagerModel>> get() = _viewPagerList

    private val _pick10ItemClickEvent = MutableLiveData<Event<Pick10Model>>()
    val pick10ItemClickEvent: LiveData<Event<Pick10Model>> get() = _pick10ItemClickEvent

    private val _topReviewClickEvent = MutableLiveData<Event<Pick10Model>>()
    val topReviewClickEvent: LiveData<Event<Pick10Model>> get() = _topReviewClickEvent

    private val _pick10ProgressBarVisible = MutableLiveData(false)
    val pick10ProgressBarVisible: LiveData<Boolean> get() = _pick10ProgressBarVisible

    private val _movieBattleItem = MutableLiveData<RatingBattleModel>()
    val movieBattleItem: LiveData<RatingBattleModel> get() = _movieBattleItem

    private val _tvBattleItem = MutableLiveData<RatingBattleModel>()
    val tvBattleItem: LiveData<RatingBattleModel> get() = _tvBattleItem

    private val _battleItemClickEvent = MutableLiveData<Event<RatingBattleModel>>()
    val battleItemClickEvent: LiveData<Event<RatingBattleModel>> get() = _battleItemClickEvent

    private val _subRankingList = MutableLiveData<List<SubRankingModel>>()
    val subRankingList: LiveData<List<SubRankingModel>> get() = _subRankingList

    private val _subRankingProgressBarVisible = MutableLiveData(false)
    val subRankingProgressBarVisible: LiveData<Boolean> get() = _subRankingProgressBarVisible

    private val _selectedTab = MutableLiveData("all")
    val selectedTab: LiveData<String> get() = _selectedTab

    private val _subRankingArrowClickEvent = MutableLiveData<Event<Unit>>()
    val subRankingArrowClickEvent: LiveData<Event<Unit>> get() = _subRankingArrowClickEvent

    private var pageCount = 1

    fun initAllData() {
        // 로딩 및 ExceptionHandler 구현 필요

        viewModelScope.launch {
            val userInfoDeferred = async { userService.getUserInfo(App.prefs.getInt(App.prefs.userIdKey)) }
            val pick10ListDeferred = async { recommendService.getPick10List(pageCount) }
            val movieBattleItemDeferred = async { recommendService.getRatingBattleItem("movie") }
            val tvBattleItemDeferred = async { recommendService.getRatingBattleItem("tv") }
            val subrankingListDeferred = async { recommendService.getSubRankingItem("all", MAX_TAKE) }

            _userInfo.value = userInfoDeferred.await()
            pick10List.value = pick10ListDeferred.await()
            _movieBattleItem.value = movieBattleItemDeferred.await()
            _tvBattleItem.value = tvBattleItemDeferred.await()
            _subRankingList.value = subrankingListDeferred.await()

            val updatedList = _subRankingList.value?.mapIndexed { index, item ->
                item.copy(rank = index + 1)
            }
            _subRankingList.value = updatedList ?: emptyList()

            _viewPagerList.value = viewPagerList()
            _progressBarVisible.value = false
        }
    }

    fun refreshPick10List() {
        viewModelScope.launch {
            _pick10ProgressBarVisible.value = true
            pick10List.value = recommendService.getPick10List(++pageCount)
            _viewPagerList.value = viewPagerList()
            _pick10ProgressBarVisible.value = false
        }
    }

    fun onBookmarkClick(item: Pick10Model) {
        // exceptionHandeler 구현 필요

        viewModelScope.launch {
            bookmarkService.postBookmarkStatus(item.contentId)

            val updatedList = pick10List.value?.map {
                if (item == it) {
                    if (it.bookmarked) {
                        it.copy(bookmarked = false)
                    } else {
                        it.copy(bookmarked = true)
                    }
                } else {
                    it
                }
            }

            pick10List.value = updatedList ?: emptyList()
            _viewPagerList.value = viewPagerList()
        }
    }

    @Suppress("SpreadOperator")
    private fun viewPagerList(): List<Pick10ViewPagerModel> {
        return listOf(
            *pick10List.value?.map { Pick10ViewPagerModel.Pick10Item(it) }?.toTypedArray() ?: emptyArray(),
            Pick10ViewPagerModel.RefreshView
        )
    }

    fun onPick10ItemClick(item: Pick10Model) {
        _pick10ItemClickEvent.value = Event(item)
    }

    fun onTopReviewClick(item: Pick10Model) {
        _topReviewClickEvent.value = Event(item)
    }

    fun onBattleItemClick(item: RatingBattleModel) {
        _battleItemClickEvent.value = Event(item)
    }

    fun onSubRankingArrowClick() {
        _subRankingArrowClickEvent.value = Event(Unit)
    }

    fun setSelectedTab(tab: String) {
        _selectedTab.value = tab
        onSubRankingTabClick()
    }

    private fun onSubRankingTabClick() {
        _subRankingProgressBarVisible.value = true

        viewModelScope.launch {
            val subrankingListDeferred =
                async { recommendService.getSubRankingItem(_selectedTab.value ?: "all", MAX_TAKE) }
            _subRankingList.value = subrankingListDeferred.await()

            val updatedList = _subRankingList.value?.mapIndexed { index, item ->
                item.copy(rank = index + 1)
            }
            _subRankingList.value = updatedList ?: emptyList()

            _subRankingProgressBarVisible.value = false
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val userService = RetrofitClient.userService
                val recommendService = RetrofitClient.recommendService
                val bookmarkService = RetrofitClient.bookmarkService
                return RecommendViewModel(userService, recommendService, bookmarkService) as T
            }
        }

        private const val MAX_TAKE = 3
    }
}
