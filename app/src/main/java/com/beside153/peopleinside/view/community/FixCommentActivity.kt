package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityFixCommentBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedAnimation
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.community.FixCommentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FixCommentActivity : BaseActivity() {
    private lateinit var binding: ActivityFixCommentBinding
    private val fixCommentViewModel: FixCommentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fix_comment)

        binding.apply {
            viewModel = fixCommentViewModel
            lifecycleOwner = this@FixCommentActivity
        }

        addBackPressedAnimation()

        val postId = intent.getLongExtra("postId", 0)
        val commentId = intent.getLongExtra("commentId", 0)
        val content = intent.getStringExtra("content") ?: ""
        fixCommentViewModel.initData(postId, commentId, content)

        fixCommentViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )

        fixCommentViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog(it) { fixCommentViewModel.onCompleteButtonClick() }
            }
        )

        fixCommentViewModel.completeFixCommentEvent.observe(
            this,
            EventObserver {
                setResult(R.string.fix_comment_complete)
                finish()
                setCloseActivityAnimation()
            }
        )
    }

    companion object {
        fun newIntent(context: Context, postId: Long, commentId: Long, content: String): Intent {
            return Intent(context, FixCommentActivity::class.java).apply {
                putExtra("postId", postId)
                putExtra("commentId", commentId)
                putExtra("content", content)
            }
        }
    }
}
