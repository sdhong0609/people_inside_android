package com.beside153.peopleinside.view.contentdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ContentDetailActivityBinding

class ContentDetailActivity : AppCompatActivity() {
    private lateinit var binding: ContentDetailActivityBinding
    private val contentDetailScreenAdapter = ContentDetailScreenAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.content_detail_activity)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.recyclerViewDetailScreen.apply {
            adapter = contentDetailScreenAdapter
            layoutManager = LinearLayoutManager(this@ContentDetailActivity)
        }

        val list = listOf(ContentDetailScreenAdapter.ContentDetailScreenModel.PosterViewItem)
        contentDetailScreenAdapter.submitList(list)
    }

    companion object {
        fun contentDetailIntent(context: Context): Intent {
            return Intent(context, ContentDetailActivity::class.java)
        }
    }
}
