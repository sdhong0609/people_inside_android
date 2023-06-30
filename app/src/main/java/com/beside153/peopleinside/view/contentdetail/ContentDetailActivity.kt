package com.beside153.peopleinside.view.contentdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityContentDetailBinding
import com.beside153.peopleinside.model.contentdetail.ContentCommentModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.view.report.ReportBottomSheetFragment
import com.beside153.peopleinside.viewmodel.contentdetail.ContentDetailViewModel

class ContentDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityContentDetailBinding
    private val contentDetailViewModel: ContentDetailViewModel by viewModels { ContentDetailViewModel.Factory }
    private val contentDetailScreenAdapter =
        ContentDetailScreenAdapter(
            ::onBookmarkClick,
            ::onCreateReviewClick,
            ::onRatingChanged,
            ::getWriterHasReview,
            ::onVerticalDotsClick,
            ::onCommentLikeClick
        )
    private val bottomSheet = ReportBottomSheetFragment()
    private var reportId = 0
    private var didClickComment = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_content_detail)

        binding.apply {
            viewModel = contentDetailViewModel
            lifecycleOwner = this@ContentDetailActivity
        }

        addBackPressedCallback { setResult(RESULT_OK) }

        val contentId = intent.getIntExtra(CONTENT_ID, 1)
        contentDetailViewModel.setContentId(contentId)
        didClickComment = intent.getBooleanExtra(DID_CLICK_COMMENT, false)
        contentDetailViewModel.initAllData(didClickComment)

        binding.contentDetailRecyclerView.apply {
            adapter = contentDetailScreenAdapter
            layoutManager = LinearLayoutManager(this@ContentDetailActivity)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                    if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                        contentDetailViewModel.loadMoreCommentList()
                    }
                }
            })
        }

        supportFragmentManager.setFragmentResultListener(
            ReportBottomSheetFragment::class.java.simpleName,
            this
        ) { _, bundle ->
            reportId = bundle.getInt(REPORT_ID)
            contentDetailViewModel.reportComment(reportId)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            contentDetailViewModel.postViewLogStay()
        }, STAY_TIME)

        initObserver()
    }

    private fun initObserver() {
        contentDetailViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                setResult(RESULT_OK)
                finish()
                setCloseActivityAnimation()
            }
        )

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
            EventObserver {
                createReviewActivityLauncher.launch(CreateReviewActivity.newIntent(this, it.first, it.second))
            }
        )

        contentDetailViewModel.verticalDotsClickEvent.observe(
            this,
            EventObserver {
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
        )

        contentDetailViewModel.reportSuccessEvent.observe(
            this,
            EventObserver {
                showToast(R.string.report_success)
            }
        )

        contentDetailViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog { contentDetailViewModel.initAllData(didClickComment) }
            }
        )
    }

    private fun onRatingChanged(rating: Float) {
        contentDetailViewModel.onRatingChanged(rating)
    }

    private fun onBookmarkClick() {
        contentDetailViewModel.onBookmarkClick()
    }

    private fun getWriterHasReview(): Boolean = contentDetailViewModel.getWriterHasReview()

    private fun onCreateReviewClick() {
        contentDetailViewModel.onCreateReviewClick()
    }

    private fun onVerticalDotsClick(item: ContentCommentModel) {
        contentDetailViewModel.onVerticalDotsClick(item)
    }

    private fun onCommentLikeClick(item: ContentCommentModel) {
        contentDetailViewModel.onCommentLikeClick(item)
    }

    private val createReviewActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                contentDetailViewModel.initAllData(false)
            }
        }

    companion object {
        private const val DID_CLICK_COMMENT = "DID_CLICK_COMMENT"
        private const val CONTENT_ID = "CONTENT_ID"
        private const val POSITION_COMMENT_LIST = 4
        private const val REPORT_ID = "REPORT_ID"
        private const val STAY_TIME = 3000L

        fun newIntent(context: Context, didClickComment: Boolean, contentId: Int): Intent {
            val intent = Intent(context, ContentDetailActivity::class.java)
            intent.putExtra(DID_CLICK_COMMENT, didClickComment)
            intent.putExtra(CONTENT_ID, contentId)
            return intent
        }
    }
}
