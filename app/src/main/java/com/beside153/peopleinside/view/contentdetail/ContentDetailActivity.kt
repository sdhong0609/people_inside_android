package com.beside153.peopleinside.view.contentdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ContentDetailActivityBinding
import com.beside153.peopleinside.view.contentdetail.ContentDetailScreenAdapter.ContentDetailScreenModel

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

        val commentList = listOf(
            ContentDetailScreenModel.CommentItem(
                "ENTP / 미소님",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미진진한 플롯이었어.리뷰 최대 세 줄까지 노출됩니다다다다다"
            ),
            ContentDetailScreenModel.CommentItem(
                "ENTP / 미소님",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어."
            ),
            ContentDetailScreenModel.CommentItem(
                "ENTP / 미소님",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미진진한 플롯이었어.리뷰"
            ),
            ContentDetailScreenModel.CommentItem(
                "ENTP / 미소님",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미진진한 플롯이었어.리뷰 최대 세 줄까지 노출됩니다다다다다"
            ),
            ContentDetailScreenModel.CommentItem(
                "ENTP / 미소님",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미진진한 플롯이었어.리뷰 최대 세 줄까지 노출됩니다다다다다"
            ),
            ContentDetailScreenModel.CommentItem(
                "ENTP / 미소님",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미진진한 플롯이었어.리뷰 최대 세 줄까지 노출됩니다다다다다"
            ),
            ContentDetailScreenModel.CommentItem(
                "ENTP / 미소님",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미진진한 플롯이었어.리뷰 최대 세 줄까지 노출됩니다다다다다"
            )
        )

        val list = mutableListOf(
            ContentDetailScreenModel.PosterView,
            ContentDetailScreenModel.ReviewView,
            ContentDetailScreenModel.InfoView,
            ContentDetailScreenModel.CommentsView
        )
        list += commentList
        contentDetailScreenAdapter.submitList(list)
    }

    companion object {
        fun contentDetailIntent(context: Context): Intent {
            return Intent(context, ContentDetailActivity::class.java)
        }
    }
}
