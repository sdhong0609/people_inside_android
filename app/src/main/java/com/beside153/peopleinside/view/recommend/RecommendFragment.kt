package com.beside153.peopleinside.view.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.MarginPageTransformer
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentRecommendBinding
import com.beside153.peopleinside.model.recommend.Pick10Model
import com.beside153.peopleinside.model.recommend.SubRankingModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.contentdetail.ContentDetailActivity
import com.beside153.peopleinside.viewmodel.recommend.RecommendViewModel

class RecommendFragment : Fragment() {
    private lateinit var binding: FragmentRecommendBinding
    private val recommendViewModel: RecommendViewModel by viewModels { RecommendViewModel.Factory }

    private val pagerAdapter =
        Pick10ViewPagerAdapter(::onPick10ItemClick, ::onTopReviewClick, ::onBookmarkClick, ::onRefreshClick)
    private val rankingAdpater = RankingRecyclerViewAdapter(::onSubRankingItemClick)
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

        binding.apply {
            viewModel = recommendViewModel
            lifecycleOwner = this@RecommendFragment
        }

        binding.pick10ViewPager.apply {
            val pagerOffsetPx = 16.dpToPx(resources.displayMetrics)
            val pagerMarginPx = 8.dpToPx(resources.displayMetrics)
            adapter = pagerAdapter
            offscreenPageLimit = 2
            setPadding(pagerOffsetPx, 0, pagerOffsetPx, 0)
            setPageTransformer(MarginPageTransformer(pagerMarginPx))
        }

        binding.subRankingRecyclerView.apply {
            adapter = rankingAdpater
            layoutManager = object : LinearLayoutManager(requireActivity()) {
                override fun canScrollVertically(): Boolean = false
            }
        }

        recommendViewModel.initAllData()

        recommendViewModel.viewPagerList.observe(viewLifecycleOwner) { list ->
            pagerAdapter.submitList(list)
        }

        recommendViewModel.pick10ItemClickEvent.observe(
            viewLifecycleOwner,
            EventObserver { item ->
                contentDetailActivityLauncher.launch(
                    ContentDetailActivity.newIntent(
                        requireActivity(),
                        false,
                        item.contentId
                    )
                )
                requireActivity().setOpenActivityAnimation()
            }
        )

        recommendViewModel.topReviewClickEvent.observe(
            viewLifecycleOwner,
            EventObserver { item ->
                startActivity(ContentDetailActivity.newIntent(requireActivity(), true, item.contentId))
                requireActivity().setOpenActivityAnimation()
            }
        )

        recommendViewModel.refreshPick10ClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                binding.pick10ViewPager.currentItem = 0
            }
        )

        recommendViewModel.battleItemClickEvent.observe(
            viewLifecycleOwner,
            EventObserver { item ->
                startActivity(ContentDetailActivity.newIntent(requireActivity(), false, item.contentId))
                requireActivity().setOpenActivityAnimation()
            }
        )

        recommendViewModel.subRankingList.observe(viewLifecycleOwner) { list ->
            rankingAdpater.submitList(list)
        }

        recommendViewModel.subRankingArrowClickEvent.observe(
            viewLifecycleOwner,
            EventObserver { mediaType ->
                startActivity(RecommendSubRankingActivity.newIntent(requireActivity(), mediaType))
                requireActivity().setOpenActivityAnimation()
            }
        )

        recommendViewModel.subRankingItemClickEvent.observe(
            viewLifecycleOwner,
            EventObserver { item ->
                startActivity(ContentDetailActivity.newIntent(requireActivity(), false, item.contentId))
                requireActivity().setOpenActivityAnimation()
            }
        )
    }

    private val contentDetailActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                recommendViewModel.initAllData()
            }
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

    private fun onTopReviewClick(item: Pick10Model) {
        recommendViewModel.onTopReviewClick(item)
    }

    private fun onBookmarkClick(item: Pick10Model) {
        recommendViewModel.onBookmarkClick(item)
    }

    private fun onRefreshClick() {
        recommendViewModel.refreshPick10List()
    }

    private fun onSubRankingItemClick(item: SubRankingModel) {
        recommendViewModel.onSubRankingItemClick(item)
    }
}
