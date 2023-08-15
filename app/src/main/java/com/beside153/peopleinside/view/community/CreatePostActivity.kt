package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityCreatePostBinding
import com.beside153.peopleinside.model.community.post.MbtiTagModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.view.dialog.OneButtonDialog
import com.beside153.peopleinside.view.dialog.TwoButtonsDialog
import com.beside153.peopleinside.viewmodel.community.CreatePostViewModel
import com.beside153.peopleinside.viewmodel.community.PostMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostActivity : BaseActivity() {
    private lateinit var binding: ActivityCreatePostBinding
    private val createPostViewModel: CreatePostViewModel by viewModels()
    private val mbtiTagAdapter = MbtiTagListAdapter(::onMbtiTagItemClick)
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post)

        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showStopPostDialog()
                }
            }
        )

        binding.apply {
            viewModel = createPostViewModel
            lifecycleOwner = this@CreatePostActivity
        }

        binding.mbtiTagRecyclerView.apply {
            adapter = mbtiTagAdapter
            layoutManager = object : GridLayoutManager(this@CreatePostActivity, MBTI_TAG_SPAN_COUNT) {
                override fun canScrollVertically(): Boolean = false
            }
        }
        val postId = intent.getLongExtra(POST_ID, 0)
        if (postId != 0L) {
            createPostViewModel.initPost(postId)
        }

        createPostViewModel.initMbtiTagList()

        createPostViewModel.mbtiTagList.observe(this) { list ->
            mbtiTagAdapter.submitList(list)
        }

        createPostViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                showStopPostDialog()
            }
        )

        createPostViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog(it) { createPostViewModel.onCompleteButtonClick() }
            }
        )

        createPostViewModel.showBadWordDialogEvent.observe(
            this,
            EventObserver {
                val badWordDialog = OneButtonDialog.OneButtonDialogBuilder()
                    .setTitleRes(R.string.bad_word_dialog_title)
                    .setDescriptionRes(R.string.bad_word_dialog_description)
                    .setButtonTextRes(R.string.fix)
                    .setButtonClickListener(object : OneButtonDialog.OneButtonDialogListener {
                        override fun onDialogButtonClick() = Unit
                    }).create()
                badWordDialog.show(supportFragmentManager, badWordDialog.tag)
            }
        )

        createPostViewModel.completePostEvent.observe(
            this,
            EventObserver { mode ->
                inputMethodManager.hideSoftInputFromWindow(binding.postContentEditText.windowToken, 0)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (mode == PostMode.CREATE) {
                        showToast(R.string.complete_create_post)
                    } else {
                        showToast(R.string.complete_fix_post)
                    }
                }, TOAST_DURATION)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (mode == PostMode.CREATE) {
                        setResult(R.string.complete_create_post)
                    } else {
                        setResult(R.string.complete_fix_post)
                    }
                    finish()
                    setCloseActivityAnimation()
                }, GO_BACK_DURATION)
            }
        )
    }

    private fun onMbtiTagItemClick(item: MbtiTagModel) {
        createPostViewModel.onMbtiTagItemClick(item)
    }

    private fun showStopPostDialog() {
        val stopPostDialog = TwoButtonsDialog.TwoButtonsDialogBuilder()
            .setTitle(R.string.stop_post_dialog_title)
            .setDescriptionRes(R.string.stop_post_dialog_description)
            .setYesText(R.string.stop_post_dialog_yes)
            .setNoText(R.string.stop_post_dialog_no)
            .setButtonClickListener(object : TwoButtonsDialog.TwoButtonsDialogListener {
                override fun onClickPositiveButton() {
                    finish()
                    setCloseActivityAnimation()
                }

                override fun onClickNegativeButton() = Unit
            }).create()
        stopPostDialog.show(supportFragmentManager, stopPostDialog.tag)
    }

    companion object {
        private const val MBTI_TAG_SPAN_COUNT = 4
        private const val TOAST_DURATION = 200L
        private const val GO_BACK_DURATION = 2000L
        private const val POST_ID = "POST_ID"

        fun newIntent(context: Context, postId: Long? = null): Intent {
            val intent = Intent(context, CreatePostActivity::class.java)
            intent.putExtra(POST_ID, postId)
            return intent
        }
    }
}
