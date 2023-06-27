package com.beside153.peopleinside.view.recommend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityRecommendSubRankingBinding
import com.beside153.peopleinside.model.recommend.SubRankingModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.contentdetail.ContentDetailActivity
import com.beside153.peopleinside.viewmodel.recommend.SubRankingViewModel

class RecommendSubRankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendSubRankingBinding
    private val subRankingViewModel: SubRankingViewModel by viewModels { SubRankingViewModel.Factory }
    private val rankingAdpater = RankingRecyclerViewAdapter(::onSubRankingItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend_sub_ranking)

        addBackPressedCallback()

        binding.subRankingRecyclerView.apply {
            adapter = rankingAdpater
            layoutManager = LinearLayoutManager(this@RecommendSubRankingActivity)
        }

        val mediaType = intent.getStringExtra(MEDIA_TYPE)
        subRankingViewModel.initData(mediaType ?: "all")

        subRankingViewModel.subRankingList.observe(this) { list ->
            rankingAdpater.submitList(list)
        }

        subRankingViewModel.subRankingItemClickEvent.observe(
            this,
            EventObserver { item ->
                startActivity(ContentDetailActivity.newIntent(this, false, item.contentId))
                setOpenActivityAnimation()
            }
        )

        binding.backImageButton.setOnClickListener {
            finish()
            setCloseActivityAnimation()
        }
    }

    private fun onSubRankingItemClick(item: SubRankingModel) {
        subRankingViewModel.onSubRankingItemClick(item)
    }

    companion object {
        private const val MEDIA_TYPE = "MEDIA_TYPE"

        fun newIntent(context: Context, mediaType: String): Intent {
            val intent = Intent(context, RecommendSubRankingActivity::class.java)
            intent.putExtra(MEDIA_TYPE, mediaType)
            return intent
        }
    }
}
