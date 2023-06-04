package com.beside153.peopleinside.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentRecommendRankingBinding
import com.beside153.peopleinside.model.RankingItem

class RecommendRankingFragment : Fragment() {
    private lateinit var binding: FragmentRecommendRankingBinding
    private val rankingAdpater = SubRankingRecyclerViewAdapter(::onRankingItemClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recommend_ranking, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.setSupportActionBar(binding.rankingToolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_left_arrow)

        activity.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // inflate 할 필요 없음
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    android.R.id.home -> {
                        activity.onBackPressed()
                    }
                }
                return true
            }
        })

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
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }
        rankingAdpater.submitList(rankingList)
    }

    private fun onRankingItemClick(item: RankingItem) {
        Toast.makeText(requireActivity(), item.title, Toast.LENGTH_SHORT).show()
    }
}
