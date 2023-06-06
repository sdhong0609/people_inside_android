package com.beside153.peopleinside.view.contentdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ContentDetailActivityBinding

class ContentDetailActivity : AppCompatActivity() {
    private lateinit var binding: ContentDetailActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.content_detail_activity)
    }
}
