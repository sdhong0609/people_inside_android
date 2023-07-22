package com.beside153.peopleinside.view.recommend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityRecommendSubRankingBinding
import com.beside153.peopleinside.model.mediacontent.SubRankingModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.contentdetail.ContentDetailActivity
import com.beside153.peopleinside.viewmodel.recommend.SubRankingViewModel

class RecommendSubRankingActivity : BaseActivity() {
    private lateinit var binding: ActivityRecommendSubRankingBinding
    private val subRankingViewModel: SubRankingViewModel by viewModels { SubRankingViewModel.Factory }
    private val rankingAdpater = RankingRecyclerViewAdapter(::onSubRankingItemClick)
    private var mediaType = "all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend_sub_ranking)

        addBackPressedCallback()

        binding.viewModel = subRankingViewModel

        binding.subRankingRecyclerView.apply {
            adapter = rankingAdpater
            layoutManager = LinearLayoutManager(this@RecommendSubRankingActivity)
        }

        mediaType = intent.getStringExtra(MEDIA_TYPE) ?: "all"
        subRankingViewModel.initData(mediaType)

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

        subRankingViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )

        subRankingViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog { subRankingViewModel.initData(mediaType) }
            }
        )
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
