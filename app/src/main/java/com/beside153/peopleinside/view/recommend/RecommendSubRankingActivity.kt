package com.beside153.peopleinside.view.recommend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityRecommendSubRankingBinding
import com.beside153.peopleinside.model.mediacontent.SubRankingModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.LinearLinelItemDecoration
import com.beside153.peopleinside.util.addBackPressedAnimation
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.contentdetail.ContentDetailActivity
import com.beside153.peopleinside.viewmodel.recommend.SubRankingViewModel

class RecommendSubRankingActivity : BaseActivity() {
    private lateinit var binding: ActivityRecommendSubRankingBinding
    private val subRankingViewModel: SubRankingViewModel by viewModels { SubRankingViewModel.Factory }
    private val rankingAdpater = SubRankingListAdapter(::onSubRankingItemClick)
    private var mediaType = "all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend_sub_ranking)

        addBackPressedAnimation()

        binding.viewModel = subRankingViewModel

        binding.subRankingRecyclerView.apply {
            adapter = rankingAdpater
            layoutManager = LinearLayoutManager(this@RecommendSubRankingActivity)
            addItemDecoration(
                LinearLinelItemDecoration(
                    1f.dpToPx(resources.displayMetrics),
                    0f,
                    ContextCompat.getColor(this@RecommendSubRankingActivity, R.color.gray_300)
                )
            )
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
