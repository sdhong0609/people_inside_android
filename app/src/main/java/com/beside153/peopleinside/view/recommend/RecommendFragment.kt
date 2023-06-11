package com.beside153.peopleinside.view.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.MarginPageTransformer
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentRecommendBinding
import com.beside153.peopleinside.model.Pick10Item
import com.beside153.peopleinside.model.RankingItem
import com.beside153.peopleinside.service.RetrofitClient.mbtiService
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.view.notification.NotificationActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendFragment : Fragment() {
    private lateinit var binding: FragmentRecommendBinding
    private lateinit var pick10ItemList: List<Pick10Item>
    private val pagerAdapter = Pick10ViewPagerAdapter()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recommendPick10Layout.pick10ViewPager.apply {
            val pagerOffsetPx = 16.dpToPx(resources.displayMetrics)
            val pagerMarginPx = 8.dpToPx(resources.displayMetrics)
            adapter = pagerAdapter
            offscreenPageLimit = 2
            setPadding(pagerOffsetPx, 0, pagerOffsetPx, 0)
            setPageTransformer(MarginPageTransformer(pagerMarginPx))
        }

        binding.recommendAppBar.notificationImageView.setOnClickListener {
            startActivity(NotificationActivity.newIntent(requireActivity()))
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
            scrollPosition = binding.recommendScrollView.scrollY
            startActivity(RecommendRankingActivity.newIntent(requireActivity()))
        }

        rankingAdpater.submitList(rankingList)

        loadPick10ItemList("esfj")
    }

    override fun onResume() {
        super.onResume()
        binding.recommendScrollView.post { binding.recommendScrollView.scrollTo(0, scrollPosition) }
    }

    private fun onRankingItemClick(item: RankingItem) {
        Toast.makeText(requireActivity(), item.title, Toast.LENGTH_SHORT).show()
    }

    private fun loadPick10ItemList(mbti: String) {
        val call = mbtiService.getTop10Content(mbti)
        call.enqueue(object : Callback<List<Pick10Item>> {
            override fun onResponse(call: Call<List<Pick10Item>>, response: Response<List<Pick10Item>>) {
                if (!response.isSuccessful || response.body() == null) {
                    Toast.makeText(requireActivity(), "데이터 불러오기를 실패했습니다", Toast.LENGTH_SHORT).show()
                    return
                }
                pick10ItemList = response.body()!!
                pagerAdapter.submitList(pick10ItemList)
            }

            override fun onFailure(call: Call<List<Pick10Item>>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
