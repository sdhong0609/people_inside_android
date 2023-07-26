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
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityContentDetailBinding
import com.beside153.peopleinside.model.mediacontent.review.ContentCommentModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedAnimation
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.view.login.LoginActivity
import com.beside153.peopleinside.view.report.ReportBottomSheetFragment
import com.beside153.peopleinside.viewmodel.contentdetail.ContentDetailViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class ContentDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityContentDetailBinding
    private val contentDetailViewModel: ContentDetailViewModel by viewModels { ContentDetailViewModel.Factory }
    private val contentDetailScreenAdapter =
        ContentDetailScreenAdapter(
            ::onBookmarkClick,
            ::onCreateReviewClick,
            ::goToLoginAcitivity,
            ::onRatingChanged,
            ::onVerticalDotsClick,
            ::onCommentLikeClick
        )
    private val bottomSheet = ReportBottomSheetFragment()
    private var reportId = 0
    private var didClickComment = false
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_content_detail)

        firebaseAnalytics = Firebase.analytics

        binding.apply {
            viewModel = contentDetailViewModel
            lifecycleOwner = this@ContentDetailActivity
        }

        addBackPressedAnimation { setResult(RESULT_OK) }

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

    @Suppress("LongMethod")
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
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.contentDetailRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                }, SCROLL_DURATION)
            }
        )

        contentDetailViewModel.createReviewClickEvent.observe(
            this,
            EventObserver {
                createReviewActivityLauncher.launch(CreateReviewActivity.newIntent(this, it.first, it.second))
                setOpenActivityAnimation()
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
            EventObserver { success ->
                if (success) {
                    showToast(R.string.report_success)
                    return@EventObserver
                }
                showToast(R.string.report_failed)
            }
        )

        contentDetailViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog(it) { contentDetailViewModel.initAllData(didClickComment) }
            }
        )

        contentDetailViewModel.createRatingEvent.observe(
            this,
            EventObserver { item ->
                firebaseAnalytics.logEvent("평가작성") {
                    param("유저_ID", App.prefs.getUserId().toString())
                    param("유저_MBTI", App.prefs.getMbti())
                    param("콘텐츠_ID", item.contentId.toString())
                    param("별점", item.rating.toString())
                }
            }
        )
    }

    private fun goToLoginAcitivity() {
        startActivity(LoginActivity.newIntent(this))
        setOpenActivityAnimation()
    }

    private fun onRatingChanged(rating: Float) {
        contentDetailViewModel.onRatingChanged(rating)
    }

    private fun onBookmarkClick() {
        if (App.prefs.getNickname() == getString(R.string.nonmember_nickname)) {
            goToLoginAcitivity()
            return
        }
        contentDetailViewModel.onBookmarkClick()
    }

    private fun onCreateReviewClick() {
        if (App.prefs.getNickname() == getString(R.string.nonmember_nickname)) {
            goToLoginAcitivity()
            return
        }
        contentDetailViewModel.onCreateReviewClick()
    }

    private fun onVerticalDotsClick(item: ContentCommentModel) {
        if (App.prefs.getNickname() == getString(R.string.nonmember_nickname)) {
            goToLoginAcitivity()
            return
        }
        contentDetailViewModel.onVerticalDotsClick(item)
    }

    private fun onCommentLikeClick(item: ContentCommentModel) {
        if (App.prefs.getNickname() == getString(R.string.nonmember_nickname)) {
            goToLoginAcitivity()
            return
        }
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
        private const val POSITION_COMMENT_LIST = 3
        private const val REPORT_ID = "REPORT_ID"
        private const val STAY_TIME = 3000L
        private const val SCROLL_DURATION = 300L

        fun newIntent(context: Context, didClickComment: Boolean, contentId: Int): Intent {
            val intent = Intent(context, ContentDetailActivity::class.java)
            intent.putExtra(DID_CLICK_COMMENT, didClickComment)
            intent.putExtra(CONTENT_ID, contentId)
            return intent
        }
    }
}
