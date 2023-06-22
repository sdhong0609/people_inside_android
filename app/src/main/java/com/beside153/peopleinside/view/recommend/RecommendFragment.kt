package com.beside153.peopleinside.view.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.MarginPageTransformer
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentRecommendBinding
import com.beside153.peopleinside.model.RankingItem
import com.beside153.peopleinside.model.recommend.Pick10Model
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.contentdetail.ContentDetailActivity
import com.beside153.peopleinside.view.notification.NotificationActivity
import com.beside153.peopleinside.viewmodel.recommend.RecommendViewModel

class RecommendFragment : Fragment() {
    private lateinit var binding: FragmentRecommendBinding
    private val recommendViewModel: RecommendViewModel by viewModels(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecommendViewModel(RetrofitClient.recommendService) as T
                }
            }
        }
    )

    private val pagerAdapter = Pick10ViewPagerAdapter(::onPick10ItemClick, ::onTopCommentClick)
    private val rankingAdpater = RankingRecyclerViewAdapter(::onRankingItemClick)
    private var scrollPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend, container, false)
        return binding.root
    }

    @Suppress("LongMethod")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutRecommendPick10.pick10ViewPager.apply {
            val pagerOffsetPx = 16.dpToPx(resources.displayMetrics)
            val pagerMarginPx = 8.dpToPx(resources.displayMetrics)
            adapter = pagerAdapter
            offscreenPageLimit = 2
            setPadding(pagerOffsetPx, 0, pagerOffsetPx, 0)
            setPageTransformer(MarginPageTransformer(pagerMarginPx))
        }

        recommendViewModel.loadPick10List()

        recommendViewModel.viewPagerList.observe(viewLifecycleOwner) { list ->
            pagerAdapter.submitList(list)
        }

        recommendViewModel.pick10ItemClickEvent.observe(
            viewLifecycleOwner,
            EventObserver { item ->
                startActivity(ContentDetailActivity.newIntent(requireActivity(), false, item.contentId))
                requireActivity().setOpenActivityAnimation()
            }
        )

        binding.recommendAppBar.notificationImageView.setOnClickListener {
            startActivity(NotificationActivity.newIntent(requireActivity()))
            requireActivity().setOpenActivityAnimation()
        }

        @Suppress("MagicNumber")
        val rankingList = listOf(
            RankingItem(
                1,
                "1",
                "어느 날 우리 집 현관으로 멸망이 들어왔다.",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.최대 2줄처리 필요합니다. 참고 부탁...",
                "전체 4.3점",
                "ISTJ 4.5점"
            ),
            RankingItem(
                2,
                "2",
                "그 해 우리는",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.이 드라마는 도전적이고 흥미...",
                "전체 4.3점",
                "ISTJ 4.5점"
            ),
            RankingItem(
                3,
                "3",
                "브람스를 좋아하세요?",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어.최대 2줄처리 필요합니다. 참고 부탁...",
                "전체 4.3점",
                "ISTJ 4.5점"
            )
        )

        binding.subRankingRecyclerView.apply {
            adapter = rankingAdpater
            layoutManager = object : LinearLayoutManager(requireActivity()) {
                override fun canScrollVertically(): Boolean = false
            }
            addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
        }

        binding.subRankingArrowImageView.setOnClickListener {
            startActivity(RecommendSubRankingActivity.newIntent(requireActivity()))
            requireActivity().setOpenActivityAnimation()
        }

        rankingAdpater.submitList(rankingList)
    }

    override fun onResume() {
        super.onResume()
        binding.recommendScrollView.post { binding.recommendScrollView.scrollTo(0, scrollPosition) }
    }

    override fun onStop() {
        super.onStop()
        scrollPosition = binding.recommendScrollView.scrollY
    }

    private fun onPick10ItemClick(item: Pick10Model) {
        recommendViewModel.onPick10ItemClick(item)
    }

    private fun onTopCommentClick() {
        startActivity(ContentDetailActivity.newIntent(requireActivity(), true, 1))
        requireActivity().setOpenActivityAnimation()
    }

    private fun onRankingItemClick(item: RankingItem) {
        Toast.makeText(requireActivity(), item.title, Toast.LENGTH_SHORT).show()
    }
}
