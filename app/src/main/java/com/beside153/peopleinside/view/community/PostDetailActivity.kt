package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityPostDetailBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedAnimation
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.community.PostDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityPostDetailBinding
    private val postDetailViewModel: PostDetailViewModel by viewModels()
    private val postDetailAdapter = PostDetailScreenAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)

        addBackPressedAnimation()

        binding.apply {
            viewModel = postDetailViewModel
            lifecycleOwner = this@PostDetailActivity
        }

        binding.postDetailRecyclerView.apply {
            adapter = postDetailAdapter
            layoutManager = LinearLayoutManager(this@PostDetailActivity)
        }

        val postId = intent.getLongExtra(POST_ID, 1)
        postDetailViewModel.setPostId(postId)
        postDetailViewModel.initAllData()

        postDetailViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )

        postDetailViewModel.screenList.observe(this) { screenList ->
            postDetailAdapter.submitList(screenList)
        }
    }

    companion object {
        private const val POST_ID = "POST_ID"

        fun newIntent(context: Context, postId: Long): Intent {
            val intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra(POST_ID, postId)
            return intent
        }
    }
}
