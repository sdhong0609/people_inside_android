package com.beside153.peopleinside.view.contentdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityCreateReviewBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.view.dialog.TwoButtonsDialog
import com.beside153.peopleinside.viewmodel.contentdetail.CreateReviewViewModel

class CreateReviewActivity : BaseActivity() {
    private lateinit var binding: ActivityCreateReviewBinding
    private val createReviewViewModel: CreateReviewViewModel by viewModels { CreateReviewViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_review)

        binding.apply {
            viewModel = createReviewViewModel
            lifecycleOwner = this@CreateReviewActivity
        }

        val contentId = intent.getIntExtra(CONTENT_ID, 1)
        val content = intent.getStringExtra(CONTENT)
        createReviewViewModel.setContentId(contentId)
        createReviewViewModel.setContent(content ?: "")

        createReviewViewModel.completeButtonClickEvent.observe(
            this,
            EventObserver {
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.completeButton.windowToken, 0)
                showToast(R.string.create_review_completed)
                Handler(Looper.getMainLooper()).postDelayed({
                    setResult(RESULT_OK)
                    finish()
                    setCloseActivityAnimation()
                }, DURATION_UNTIL_BACK)
            }
        )

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showCancelReviewDialog()
                }
            }
        )

        createReviewViewModel.backButtonClickEvent.observe(this) {
            showCancelReviewDialog()
        }

        createReviewViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog { createReviewViewModel.onCompleteButtonClick() }
            }
        )
    }

    private fun showCancelReviewDialog() {
        val cancelReviewDialog = TwoButtonsDialog.TwoButtonsDialogBuilder()
            .setTitle(R.string.will_you_cancel)
            .setDescription(R.string.will_you_cancel_content)
            .setButtonClickListener(object : TwoButtonsDialog.TwoButtonsDialogListener {
                override fun onClickPositiveButton() {
                    finish()
                    setCloseActivityAnimation()
                }

                override fun onClickNegativeButton() = Unit
            }).create()
        cancelReviewDialog.show(supportFragmentManager, cancelReviewDialog.tag)
    }

    companion object {
        private const val DURATION_UNTIL_BACK = 2000L
        private const val CONTENT_ID = "CONTENT_ID"
        private const val CONTENT = "CONTENT"

        fun newIntent(context: Context, contentId: Int, content: String): Intent {
            val intent = Intent(context, CreateReviewActivity::class.java)
            intent.putExtra(CONTENT_ID, contentId)
            intent.putExtra(CONTENT, content)
            return intent
        }
    }
}
