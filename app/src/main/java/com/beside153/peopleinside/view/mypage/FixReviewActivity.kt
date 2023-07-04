package com.beside153.peopleinside.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityFixReviewBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.view.commonview.CancelReviewDialog
import com.beside153.peopleinside.view.commonview.CancelReviewDialogInterface
import com.beside153.peopleinside.viewmodel.mypage.FixReviewViewModel

class FixReviewActivity : BaseActivity(), CancelReviewDialogInterface {
    private lateinit var binding: ActivityFixReviewBinding
    private val fixReviewViewModel: FixReviewViewModel by viewModels { FixReviewViewModel.Factory }
    private val cancelReviewDialog = CancelReviewDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fix_review)

        addBackPressedCallback()

        binding.apply {
            viewModel = fixReviewViewModel
            lifecycleOwner = this@FixReviewActivity
        }

//        fixReviewViewModel.setContent(content ?: "")

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showCancelReviewDialog()
                }
            }
        )

        fixReviewViewModel.completeButtonClickEvent.observe(
            this,
            EventObserver {
                val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.completeButton.windowToken, 0)
                setResult(RESULT_OK)
                finish()
            }
        )

        fixReviewViewModel.backButtonClickEvent.observe(this) {
            showCancelReviewDialog()
        }

        fixReviewViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog { fixReviewViewModel.onCompleteButtonClick() }
            }
        )
    }

    override fun onDialogYesButtonClick() {
        finish()
        setCloseActivityAnimation()
    }

    private fun showCancelReviewDialog() {
        cancelReviewDialog.show(this.supportFragmentManager, cancelReviewDialog.tag)
    }

    companion object {
        private const val CONTENT_ID = "CONTENT_ID"
        private const val CONTENT = "CONTENT"

        fun newIntent(context: Context, contentId: Int, content: String): Intent {
            val intent = Intent(context, FixReviewActivity::class.java)
            intent.putExtra(CONTENT_ID, contentId)
            intent.putExtra(CONTENT, content)
            return intent
        }
    }
}
