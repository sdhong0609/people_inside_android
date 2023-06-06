package com.beside153.peopleinside.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.CreateReviewActivityBinding

class CreateReviewActivity : AppCompatActivity() {
    private lateinit var binding: CreateReviewActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.create_review_activity)

        binding.buttonClose.setOnClickListener {
            finish()
        }

        binding.buttonComplete.setOnClickListener {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.buttonComplete.windowToken, 0)
            binding.textViewCompleteReview.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, DURATION_UNTIL_BACK)
        }
    }

    companion object {
        private const val DURATION_UNTIL_BACK = 2000L

        fun createReviewIntent(context: Context): Intent {
            return Intent(context, CreateReviewActivity::class.java)
        }
    }
}
