package com.beside153.peopleinside.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityMypageRatingContentsBinding

class RatingContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageRatingContentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_rating_contents)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, RatingContentsActivity::class.java)
        }
    }
}
