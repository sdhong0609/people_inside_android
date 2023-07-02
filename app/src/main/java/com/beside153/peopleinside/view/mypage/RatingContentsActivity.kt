package com.beside153.peopleinside.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityMypageRatingContentsBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.mypage.RatingContentsViewModel

class RatingContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageRatingContentsBinding
    private val contentsViewModel: RatingContentsViewModel by viewModels { RatingContentsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_rating_contents)

        addBackPressedCallback { setResult(RESULT_OK) }

        binding.apply {
            viewModel = contentsViewModel
            lifecycleOwner = this@RatingContentsActivity
        }

        contentsViewModel.initAllData()

        contentsViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                setResult(RESULT_OK)
                finish()
                setCloseActivityAnimation()
            }
        )
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, RatingContentsActivity::class.java)
        }
    }
}
