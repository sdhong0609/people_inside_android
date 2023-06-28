package com.beside153.peopleinside.view.contentdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityCreateReviewBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.viewmodel.contentdetail.CancelReviewDialog
import com.beside153.peopleinside.viewmodel.contentdetail.CancelReviewDialogInterface
import com.beside153.peopleinside.viewmodel.contentdetail.CreateReviewViewModel

class CreateReviewActivity : AppCompatActivity(), CancelReviewDialogInterface {
    private lateinit var binding: ActivityCreateReviewBinding
    private val createReviewViewModel: CreateReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_review)

        binding.apply {
            viewModel = createReviewViewModel
            lifecycleOwner = this@CreateReviewActivity
        }

        createReviewViewModel.backButtonClickEvent.observe(this) {
            CancelReviewDialog(this).show(this.supportFragmentManager, CANCEL_REVIEW_DIALOG)
        }

        createReviewViewModel.completeButtonClickEvent.observe(
            this,
            EventObserver {
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.completeTextView.windowToken, 0)
                showToast(R.string.create_review_completed)
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, DURATION_UNTIL_BACK)
            }
        )
    }

    companion object {
        private const val DURATION_UNTIL_BACK = 2000L
        private const val CANCEL_REVIEW_DIALOG = "CANCEL_REVIEW_DIALOG"

        fun newIntent(context: Context): Intent {
            return Intent(context, CreateReviewActivity::class.java)
        }
    }

    override fun onDialogYesButtonClick() {
        finish()
    }
}
