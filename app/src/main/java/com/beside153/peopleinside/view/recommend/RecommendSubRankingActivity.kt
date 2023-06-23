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
import com.beside153.peopleinside.databinding.ActivityRecommendSubRankingBinding
import com.beside153.peopleinside.model.recommend.SubRankingModel
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation

class RecommendSubRankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendSubRankingBinding
    private val rankingAdpater = RankingRecyclerViewAdapter(::onSubRankingItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend_sub_ranking)

        addBackPressedCallback()

        binding.subRankingRecyclerView.apply {
            adapter = rankingAdpater
            layoutManager = LinearLayoutManager(this@RecommendSubRankingActivity)
            addItemDecoration(DividerItemDecoration(this@RecommendSubRankingActivity, LinearLayoutManager.VERTICAL))
        }
//        rankingAdpater.submitList(rankingList)

        binding.backImageButton.setOnClickListener {
            finish()
            setCloseActivityAnimation()
        }
    }

    private fun onSubRankingItemClick(item: SubRankingModel) {
        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RecommendSubRankingActivity::class.java)
        }
    }
}
