package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityCommunitySearchBinding
import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.viewmodel.community.CommunitySearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunitySearchActivity : BaseActivity() {
    private lateinit var binding: ActivityCommunitySearchBinding
    private val communitySearchViewModel: CommunitySearchViewModel by viewModels()
    private val postListAdapter = CommunityPostListAdapter(::onPostItemClick)

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
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                communitySearchViewModel.searchPostAction()
                return@setOnEditorActionListener true
            }
            false
        }

        communitySearchViewModel.error.observe(
            this,
            EventObserver {
                //
            }
        )

        communitySearchViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                overridePendingTransition(0, 0)
            }
        )

        communitySearchViewModel.searchedPostList.observe(this) { list ->
            postListAdapter.submitList(list)
        }

        communitySearchViewModel.postItemClickEvent.observe(
            this,
            EventObserver { postId ->
                activityLauncher.launch(PostDetailActivity.newIntent(this, postId))
                setOpenActivityAnimation()
            }
        )
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
