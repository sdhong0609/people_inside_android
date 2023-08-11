package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityFixCommentBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedAnimation
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.community.FixCommentViewModel

class FixCommentActivity : BaseActivity() {
    private lateinit var binding: ActivityFixCommentBinding
    private val fixCommentViewModel: FixCommentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fix_comment)

        binding.apply {
            viewModel = fixCommentViewModel
            lifecycleOwner = this@FixCommentActivity
        }

        addBackPressedAnimation()

        fixCommentViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, FixCommentActivity::class.java)
    }
}
