package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityCreatePostBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedAnimation
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.community.CreatePostViewModel

class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding
    private val createPostViewModel: CreatePostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post)

        addBackPressedAnimation()

        binding.apply {
            viewModel = createPostViewModel
            lifecycleOwner = this@CreatePostActivity
        }

        createPostViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, CreatePostActivity::class.java)
    }
}
