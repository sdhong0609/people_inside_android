package com.beside153.peopleinside.view.contentdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityContentDetailBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.contentdetail.ContentDetailViewModel

class ContentDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentDetailBinding
    private val contentDetailScreenAdapter = ContentDetailScreenAdapter(::onBookmarkClick, ::onCreateReviewClick)
    private val contentDetailViewModel: ContentDetailViewModel by viewModels { ContentDetailViewModel.Factory }
    private var contentId: Int = 1

    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_content_detail)

        binding.apply {
            viewModel = contentDetailViewModel
            lifecycleOwner = this@ContentDetailActivity
        }

        addBackPressedCallback()

        contentId = intent.getIntExtra(CONTENT_ID, 1)
        val didClickComment = intent.getBooleanExtra(DID_CLICK_COMMENT, false)
        contentDetailViewModel.initAllData(contentId, didClickComment)

        contentDetailViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )

        binding.contentDetailRecyclerView.apply {
            adapter = contentDetailScreenAdapter
            layoutManager = LinearLayoutManager(this@ContentDetailActivity)
        }

        contentDetailViewModel.screenList.observe(this) { screenList ->
            contentDetailScreenAdapter.submitList(screenList)
        }

        contentDetailViewModel.scrollEvent.observe(
            this,
            EventObserver {
                val smoothScroller = object : LinearSmoothScroller(this) {
                    override fun getVerticalSnapPreference(): Int = SNAP_TO_START
                }
                smoothScroller.targetPosition = POSITION_COMMENT_LIST
                binding.contentDetailRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
            }
        )

        contentDetailViewModel.createReviewClickEvent.observe(
            this,
            EventObserver { contentId ->
                startActivity(CreateReviewActivity.newIntent(this, contentId))
            }
        )
    }

    private fun onBookmarkClick() {
        contentDetailViewModel.onBookmarkClick(contentId)
    }

    private fun onCreateReviewClick() {
        contentDetailViewModel.onCreateReviewClick(contentId)
    }

    companion object {
        private const val DID_CLICK_COMMENT = "DID_CLICK_COMMENT"
        private const val CONTENT_ID = "CONTENT_ID"
        private const val POSITION_COMMENT_LIST = 4

        fun newIntent(context: Context, didClickComment: Boolean, contentId: Int): Intent {
            val intent = Intent(context, ContentDetailActivity::class.java)
            intent.putExtra(DID_CLICK_COMMENT, didClickComment)
            intent.putExtra(CONTENT_ID, contentId)
            return intent
        }
    }
}
