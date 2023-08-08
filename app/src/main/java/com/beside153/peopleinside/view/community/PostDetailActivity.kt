package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityPostDetailBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.community.PostDetailViewModel

class PostDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityPostDetailBinding
    private val postDetailViewModel: PostDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)

        binding.apply {
            viewModel = postDetailViewModel
            lifecycleOwner = this@PostDetailActivity
        }

        postDetailViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, PostDetailActivity::class.java)
    }
}
