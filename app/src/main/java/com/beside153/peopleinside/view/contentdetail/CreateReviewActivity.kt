package com.beside153.peopleinside.view.contentdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityCreateReviewBinding

class CreateReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_review)

        binding.closeButton.setOnClickListener {
            finish()
        }

        binding.completeTextView.setOnClickListener {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.completeTextView.windowToken, 0)
            Toast.makeText(this, getString(R.string.create_review_completed), Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, DURATION_UNTIL_BACK)
        }
    }

    companion object {
        private const val DURATION_UNTIL_BACK = 2000L

        fun newIntent(context: Context): Intent {
            return Intent(context, CreateReviewActivity::class.java)
        }
    }
}
