package com.beside153.peopleinside.view.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.NotificationActivityBinding
import com.beside153.peopleinside.model.notification.NotificationItem
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: NotificationActivityBinding
    private val notificationAdapter = NotificationListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.notification_activity)

        addBackPressedCallback()

        val notificationItemList = listOf(
            NotificationItem(
                getString(R.string.notification_message_emoji),
                "하트 도착",
                "ENFJ 미소님이 적어주신 댓글에 ENFP 민지님 외 9명이 하트로 공감했어요.\uD83E\uDD70",
                "10분 전"
            ),
            NotificationItem(
                getString(R.string.notification_bulb_emoji),
                "NEW 추천",
                "피플인사이드와 몰입러들이 추천하는 새로운 작품이 업로드되었답니다. 한번 확인해보세요.\uD83D\uDE4B\u200D♀️",
                "20분 전"
            ),
            NotificationItem(
                getString(R.string.notification_message_emoji),
                "하트 도착",
                "ENFJ 미소님이 적어주신 댓글에 ENFP 민지님 외 9명이 하트로 공감했어요.\uD83E\uDD70",
                "2분 전"
            ),
            NotificationItem(
                getString(R.string.notification_bulb_emoji),
                "NEW 추천",
                "피플인사이드와 몰입러들이 추천하는 새로운 작품이 업로드되었답니다. 한번 확인해보세요.\uD83D\uDE4B\u200D♀️",
                "40분 전"
            ),
            NotificationItem(
                getString(R.string.notification_message_emoji),
                "하트 도착",
                "ENFJ 미소님이 적어주신 댓글에 ENFP 민지님 외 9명이 하트로 공감했어요.\uD83E\uDD70",
                "2분 전"
            ),
            NotificationItem(
                getString(R.string.notification_bulb_emoji),
                "NEW 추천",
                "피플인사이드와 몰입러들이 추천하는 새로운 작품이 업로드되었답니다. 한번 확인해보세요.\uD83D\uDE4B\u200D♀️",
                "5분 전"
            ),
            NotificationItem(
                getString(R.string.notification_message_emoji),
                "하트 도착",
                "ENFJ 미소님이 적어주신 댓글에 ENFP 민지님 외 9명이 하트로 공감했어요.\uD83E\uDD70",
                "2분 전"
            )
        )

        binding.recyclerViewNotification.apply {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(this@NotificationActivity)
        }
        notificationAdapter.submitList(notificationItemList)

        binding.buttonBack.setOnClickListener {
            finish()
            setCloseActivityAnimation()
        }
    }

    companion object {
        fun notificationIntent(context: Context): Intent {
            return Intent(context, NotificationActivity::class.java)
        }
    }
}
