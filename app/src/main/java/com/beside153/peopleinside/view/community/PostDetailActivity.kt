package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityPostDetailBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.KeyboardVisibilityUtils
import com.beside153.peopleinside.util.addBackPressedAnimation
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.view.report.BottomSheetFragment
import com.beside153.peopleinside.view.report.BottomSheetType
import com.beside153.peopleinside.viewmodel.community.PostDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityPostDetailBinding
    private val postDetailViewModel: PostDetailViewModel by viewModels()
    private val postDetailAdapter = PostDetailScreenAdapter()
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)
        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        addBackPressedAnimation()

        KeyboardVisibilityUtils(
            window,
            onShowKeyboard = { _, _ ->
                // 키보드가 올라올 때의 동작
                postDetailViewModel.setUploadButtonVisible(true)
            },
            onHideKeyboard = {
                // 키보드가 내려갈 때의 동작
                postDetailViewModel.setUploadButtonVisible(false)
            }
        )

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

        postDetailViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog(it) { postDetailViewModel.initAllData() }
            }
        )

        postDetailViewModel.screenList.observe(this) { screenList ->
            postDetailAdapter.submitList(screenList)
        }

        postDetailViewModel.completeUploadCommentEvent.observe(
            this,
            EventObserver {
                inputMethodManager.hideSoftInputFromWindow(binding.commentEditText.windowToken, 0)
                binding.commentEditText.clearFocus()
                postDetailViewModel.initAllData()
            }
        )

        postDetailViewModel.postVerticalDotsClickEvent.observe(
            this,
            EventObserver { isMyPost ->
                if (isMyPost) {
                    val bottomSheet = BottomSheetFragment(BottomSheetType.PostFixDelete)
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                    return@EventObserver
                }
            }
        )

        supportFragmentManager.setFragmentResultListener(
            BottomSheetFragment::class.java.simpleName,
            this
        ) { _, bundle ->
//            reportId = bundle.getInt(REPORT_ID)
//            contentDetailViewModel.reportComment(reportId)
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
