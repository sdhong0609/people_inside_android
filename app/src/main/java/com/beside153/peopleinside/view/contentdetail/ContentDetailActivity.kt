package com.beside153.peopleinside.view.contentdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
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

        val didClickComment = intent.getBooleanExtra(DID_CLICK_COMMENT, false)
        if (didClickComment) {
            val smoothScroller = object : LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference(): Int = SNAP_TO_START
            }
            smoothScroller.targetPosition = POSITION_COMMENT_LIST
            binding.recyclerViewDetailScreen.layoutManager?.startSmoothScroll(smoothScroller)
        }
    }

    companion object {
        private const val DID_CLICK_COMMENT = "DID_CLICK_COMMENT"
        private const val POSITION_COMMENT_LIST = 3

        fun contentDetailIntent(context: Context, didClickComment: Boolean): Intent {
            val intent = Intent(context, ContentDetailActivity::class.java)
            intent.putExtra(DID_CLICK_COMMENT, didClickComment)
            return intent
        }
    }
}
