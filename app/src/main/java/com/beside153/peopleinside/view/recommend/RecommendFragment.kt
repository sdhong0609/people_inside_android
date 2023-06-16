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
import com.beside153.peopleinside.model.Review
import com.beside153.peopleinside.model.Writer
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.contentdetail.ContentDetailActivity
import com.beside153.peopleinside.view.notification.NotificationActivity

class RecommendFragment : Fragment() {
    private lateinit var binding: FragmentRecommendBinding

    // 추천 API 붙일 때 다시 사용할 예정입니다!
    // private lateinit var pick10ItemList: List<Pick10Item>
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

        binding.recommendAppBar.notificationImageView.setOnClickListener {
            startActivity(NotificationActivity.newIntent(requireActivity()))
            requireActivity().setOpenActivityAnimation()
        }

        @Suppress("MagicNumber")
        val pick10MockDatList = listOf(
            Pick10Item(
                1,
                "어느 날 우리 집 현관으로 멸망이 들어왔다.",
                "어느 날 우리 집 현관으로 멸망이 들어왔다.",
                "",
                4.3,
                4.5,
                true,
                Review(
                    1,
                    "2023-06-06T14:57:47.063Z",
                    "너무 감동적이에요 ㅠㅠ 😥",
                    15,
                    1,
                    1,
                    Writer(1, "2023-06-06T14:57:47.063Z", null, "account", "password", "admin", "admin")
                )
            ),
            Pick10Item(
                2,
                "베놈",
                "어느 날 우리 집 현관으로 멸망이 들어왔다.",
                "",
                4.3,
                4.5,
                true
            ),
            Pick10Item(
                3,
                "어느 날 우리 집 현관으로 멸망이 들어왔다.",
                "어느 날 우리 집 현관으로 멸망이 들어왔다.",
                "",
                4.3,
                4.5,
                true
            )
        )

        pagerAdapter.submitList(pick10MockDatList)

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

        // 추천 API 붙일 때 다시 사용할 예정입니다!
        // loadPick10ItemList("esfj")
    }

    override fun onResume() {
        super.onResume()
        binding.recommendScrollView.post { binding.recommendScrollView.scrollTo(0, scrollPosition) }
    }

    override fun onStop() {
        super.onStop()
        scrollPosition = binding.recommendScrollView.scrollY
    }

    private fun onPick10ItemClick() {
        startActivity(ContentDetailActivity.newIntent(requireActivity(), false))
        requireActivity().setOpenActivityAnimation()
    }

    private fun onTopCommentClick() {
        startActivity(ContentDetailActivity.newIntent(requireActivity(), true))
        requireActivity().setOpenActivityAnimation()
    }

    private fun onRankingItemClick(item: RankingItem) {
        Toast.makeText(requireActivity(), item.title, Toast.LENGTH_SHORT).show()
    }

    // 추천 API 붙일 때 다시 사용할 예정입니다!
//    private fun loadPick10ItemList(mbti: String) {
//        val call = mbtiService.getTop10Content(mbti)
//        call.enqueue(object : Callback<List<Pick10Item>> {
//            override fun onResponse(call: Call<List<Pick10Item>>, response: Response<List<Pick10Item>>) {
//                if (!response.isSuccessful || response.body() == null) {
//                    Toast.makeText(requireActivity(), "데이터 불러오기를 실패했습니다", Toast.LENGTH_SHORT).show()
//                    return
//                }
//                pick10ItemList = response.body()!!
//                pagerAdapter.submitList(pick10ItemList)
//            }
//
//            override fun onFailure(call: Call<List<Pick10Item>>, t: Throwable) {
//                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}
