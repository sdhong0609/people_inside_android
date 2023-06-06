package com.beside153.peopleinside.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
    }

    companion object {

        fun createReviewIntent(context: Context): Intent {
            return Intent(context, CreateReviewActivity::class.java)
        }
    }
}
