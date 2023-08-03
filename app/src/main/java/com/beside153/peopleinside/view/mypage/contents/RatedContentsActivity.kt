package com.beside153.peopleinside.view.mypage.contents

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityMypageRatingContentsBinding
import com.beside153.peopleinside.model.mycontent.RatedContentModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.LinearLinelItemDecoration
import com.beside153.peopleinside.util.addBackPressedAnimation
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.viewmodel.mypage.contents.RatedContentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatedContentsActivity : BaseActivity() {
    private lateinit var binding: ActivityMypageRatingContentsBinding
    private val contentsViewModel: RatedContentsViewModel by viewModels()
    private val contentListAdapter = RatedContentListAdapter(::onRatingChanged, ::onVerticalDotsClick)
    private lateinit var popupView: View
    private lateinit var popupWindow: PopupWindow
    private lateinit var reviewFixTextView: TextView
    private lateinit var reviewDeleteTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_rating_contents)

        addBackPressedAnimation { setResult(RESULT_OK) }

        contentsViewModel.initAllData()

        initPopupWindow()

        binding.apply {
            viewModel = contentsViewModel
            lifecycleOwner = this@RatedContentsActivity
        }

        binding.ratingContentsRecyclerView.apply {
            adapter = contentListAdapter
            layoutManager = LinearLayoutManager(this@RatedContentsActivity)
            addItemDecoration(
                LinearLinelItemDecoration(
                    8f.dpToPx(resources.displayMetrics),
                    0f,
                    ContextCompat.getColor(this@RatedContentsActivity, R.color.gray_300)
                )
            )
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                    if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                        contentsViewModel.loadMoreData()
                    }
                }
            })
        }

        contentsViewModel.contentList.observe(this) { list ->
            contentListAdapter.submitList(list)
        }

        contentsViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                setResult(RESULT_OK)
                finish()
                setCloseActivityAnimation()
            }
        )

        contentsViewModel.reviewFixClickEvent.observe(
            this,
            EventObserver { item ->
                fixReviewActivityLauncher.launch(
                    FixReviewActivity.newIntent(this, item)
                )
                setOpenActivityAnimation()
                popupWindow.dismiss()
            }
        )

        contentsViewModel.reviewDeleteClickEvent.observe(
            this,
            EventObserver { item ->
                contentsViewModel.deleteReview(item)
                popupWindow.dismiss()
            }
        )

        contentsViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog(it) { contentsViewModel.initAllData() }
            }
        )
    }

    private fun initPopupWindow() {
        popupView = layoutInflater.inflate(R.layout.popup_window_rating_content, null)

        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )

        reviewFixTextView = popupView.findViewById(R.id.reviewFixTextView)
        reviewDeleteTextView = popupView.findViewById(R.id.reviewDeleteTextView)
    }

    private val fixReviewActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val fixedItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(FIXED_ITEM, RatedContentModel::class.java)
                } else {
                    result.data?.getParcelableExtra(FIXED_ITEM)
                }

                contentsViewModel.updateFixedReview(fixedItem!!)
            }
        }

    private fun onVerticalDotsClick(imageView: ImageView, item: RatedContentModel) {
        val location = IntArray(2)
        imageView.getLocationOnScreen(location)
        val x = location[0]
        val width = imageView.width

        // 팝업 창의 너비와 높이를 가져옵니다.
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val popupWidth = popupView.measuredWidth

        // 디바이스 화면의 가로 크기를 가져옵니다.
        val screenWidth = DisplayMetrics().widthPixels

        // 팝업 창을 표시할 위치를 계산합니다.
        val offsetX = if (x + width + popupWidth > screenWidth) {
            // 오른쪽으로 넘어가는 경우
            -(popupWidth - width)
        } else {
            // 오른쪽으로 넘어가지 않는 경우
            0
        }

        reviewFixTextView.setOnClickListener {
            contentsViewModel.onReviewFixClick(item)
        }

        reviewDeleteTextView.setOnClickListener {
            contentsViewModel.onReviewDeleteClick(item)
        }

        popupWindow.showAsDropDown(imageView, offsetX - OFFSET, 0)
    }

    private fun onRatingChanged(rating: Float, item: RatedContentModel) {
        contentsViewModel.onRatingChanged(rating, item)
    }

    companion object {
        private const val OFFSET = 15
        private const val FIXED_ITEM = "FIXED_ITEM"

        fun newIntent(context: Context): Intent {
            return Intent(context, RatedContentsActivity::class.java)
        }
    }
}
