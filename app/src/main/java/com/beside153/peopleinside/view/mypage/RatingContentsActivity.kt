package com.beside153.peopleinside.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityMypageRatingContentsBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.mypage.RatingContentsViewModel

class RatingContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageRatingContentsBinding
    private val contentsViewModel: RatingContentsViewModel by viewModels { RatingContentsViewModel.Factory }
    private val contentListAdapter = RatingContentsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_rating_contents)

        addBackPressedCallback { setResult(RESULT_OK) }

        contentsViewModel.initAllData()

        binding.apply {
            viewModel = contentsViewModel
            lifecycleOwner = this@RatingContentsActivity
        }

        binding.ratingContentsRecyclerView.apply {
            adapter = contentListAdapter
            layoutManager = LinearLayoutManager(this@RatingContentsActivity)
        }

        contentsViewModel.contentList.observe(this) { list ->
            contentListAdapter.submitList(list)
        }

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
