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
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.common.BottomSheetFragment
import com.beside153.peopleinside.view.common.BottomSheetType
import com.beside153.peopleinside.view.dialog.TwoButtonsDialog
import com.beside153.peopleinside.viewmodel.community.PostDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityPostDetailBinding
    private val postDetailViewModel: PostDetailViewModel by viewModels()
    private lateinit var postDetailAdapter: PostDetailScreenAdapter
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)
        postDetailAdapter = PostDetailScreenAdapter(postDetailViewModel)
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

        val postId = intent.getLongExtra(POST_ID, 0)
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

        postDetailViewModel.completeDeletePostEvent.observe(
            this,
            EventObserver {
                setResult(R.string.delete_post_dialog_title)
                finish()
                setCloseActivityAnimation()
            }
        )

        supportFragmentManager.setFragmentResultListener(
            BottomSheetFragment::class.java.simpleName,
            this
        ) { _, bundle ->
            val fixOrDelete = bundle.getString(FIX_DELETE)
            if (fixOrDelete == getString(R.string.fix)) {
                startActivity(CreatePostActivity.newIntent(this, postId))
                setOpenActivityAnimation()
                return@setFragmentResultListener
            }
            if (fixOrDelete == getString(R.string.delete)) {
                showDeletePostDialog()
                return@setFragmentResultListener
            }
        }
    }

    private fun showDeletePostDialog() {
        val deletePostDialog = TwoButtonsDialog.TwoButtonsDialogBuilder()
            .setTitle(R.string.delete_post_dialog_title)
            .setDescription(R.string.delete_post_dialog_description)
            .setButtonClickListener(object : TwoButtonsDialog.TwoButtonsDialogListener {
                override fun onClickPositiveButton() {
                    postDetailViewModel.deletePost()
                }

                override fun onClickNegativeButton() = Unit
            }).create()
        deletePostDialog.show(supportFragmentManager, deletePostDialog.tag)
    }

    companion object {
        private const val POST_ID = "POST_ID"
        private const val FIX_DELETE = "FIX_DELETE"

        fun newIntent(context: Context, postId: Long): Intent {
            val intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra(POST_ID, postId)
            return intent
        }
    }
}
