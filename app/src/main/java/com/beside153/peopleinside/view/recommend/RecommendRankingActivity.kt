package com.beside153.peopleinside.view.recommend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityRecommendRankingBinding
import com.beside153.peopleinside.model.RankingItem

class RecommendRankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendRankingBinding
    private val rankingAdpater = RankingRecyclerViewAdapter(::onRankingItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend_ranking)

        val rankingList = listOf(
            RankingItem(
                "1",
                "어느 날 우리 집 현관으로 멸망이 들어왔다.",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.최대 2줄처리 필요합니다. 참고 부탁...",
                "전체 4.3점",
                "ISTJ 4.5점"
            ),
            RankingItem(
                "2",
                "그 해 우리는",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미...",
                "전체 4.3점",
                "ISTJ 4.5점"
            ),
            RankingItem(
                "3",
                "브람스를 좋아하세요?",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.최대 2줄처리 필요합니다. 참고 부탁...",
                "전체 4.3점",
                "ISTJ 4.5점"
            ),
            RankingItem(
                "1",
                "어느 날 우리 집 현관으로 멸망이 들어왔다.",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.최대 2줄처리 필요합니다. 참고 부탁...",
                "전체 4.3점",
                "ISTJ 4.5점"
            ),
            RankingItem(
                "2",
                "그 해 우리는",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미...",
                "전체 4.3점",
                "ISTJ 4.5점"
            ),
            RankingItem(
                "3",
                "브람스를 좋아하세요?",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.최대 2줄처리 필요합니다. 참고 부탁...",
                "전체 4.3점",
                "ISTJ 4.5점"
            )
        )

        binding.recyclerViewRanking.apply {
            adapter = rankingAdpater
            layoutManager = LinearLayoutManager(this@RecommendRankingActivity)
            addItemDecoration(DividerItemDecoration(this@RecommendRankingActivity, LinearLayoutManager.VERTICAL))
        }
        rankingAdpater.submitList(rankingList)

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun onRankingItemClick(item: RankingItem) {
        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun recommendRankingIntent(context: Context): Intent {
            return Intent(context, RecommendRankingActivity::class.java)
        }
    }
}
