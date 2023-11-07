package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.common.extension.eventObserve
import com.beside153.peopleinside.databinding.ActivityCommunitySearchBinding
import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.viewmodel.community.CommunitySearchViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunitySearchActivity : BaseActivity() {
    private lateinit var binding: ActivityCommunitySearchBinding
    private val communitySearchViewModel: CommunitySearchViewModel by viewModels()
    private val postListAdapter = CommunityPostListAdapter(::onPostItemClick)
    private val recentSearchWordAdapter = RecentSearchWordAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_search)

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                    overridePendingTransition(0, 0)
                }
            }
        )

        binding.apply {
            viewModel = communitySearchViewModel
            lifecycleOwner = this@CommunitySearchActivity
            searchedPostRecyclerView.adapter = postListAdapter
            searchedPostRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                    if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                        communitySearchViewModel.loadMorePostList()
                    }
                }
            })
        }

        FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            binding.recentSearchRecyclerView.apply {
                layoutManager = it
                adapter = recentSearchWordAdapter
            }
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                communitySearchViewModel.searchPostAction()
                return@setOnEditorActionListener true
            }
            false
        }

        initObserver()
        communitySearchViewModel.initSearchWordList()
    }

    private fun initObserver() {
        communitySearchViewModel.error.eventObserve(this) {
            showErrorDialog(it) { communitySearchViewModel.searchPostAction() }
        }

        communitySearchViewModel.backButtonClickEvent.eventObserve(this) {
            finish()
            overridePendingTransition(0, 0)
        }

        communitySearchViewModel.searchWordList.observe(this) { list ->
            recentSearchWordAdapter.submitList(list)
        }

        communitySearchViewModel.searchedPostList.observe(this) { list ->
            postListAdapter.submitList(list)
        }

        communitySearchViewModel.postItemClickEvent.eventObserve(this) { postId ->
            activityLauncher.launch(PostDetailActivity.newIntent(this, postId))
            setOpenActivityAnimation()
        }
    }

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                communitySearchViewModel.searchPostAction()
                return@registerForActivityResult
            }
        }

    private fun onPostItemClick(item: CommunityPostModel) {
        communitySearchViewModel.onPostItemClick(item)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, CommunitySearchActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            return intent
        }
    }
}
