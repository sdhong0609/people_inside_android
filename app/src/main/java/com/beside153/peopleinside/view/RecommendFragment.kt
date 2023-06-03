package com.beside153.peopleinside.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentRecommendBinding
import com.beside153.peopleinside.model.Top10Item

class RecommendFragment : Fragment() {
    private lateinit var binding: FragmentRecommendBinding

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

        val dataList = mutableListOf<Top10Item>(
            Top10Item(
                "어느 날 우리 집 현관으로 멸망이 들어왔다 adfadfadfsadfadfadfafsd",
                "전체 3.5점",
                "ENTJ 2.1점",
                "ESFJ / 피자치킨님",
                "이 드라마는 도전적"
            ),
            Top10Item(
                "어느 날 우리 집",
                "전체 4.0점",
                "ISTJ 3.3점",
                "ESFJ / 피자치킨님",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어. 이 드라마는 도전적이고 흥미진진한 플롯이었어. 리뷰 최대 세 줄까지 노출..."
            ),
            Top10Item(
                "어느 날 우리 집 현관으로 멸망이 들어왔다",
                "전체 4.0점",
                "ESTJ 4.3점",
                "ESFJ / 피자치킨님",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어. 이 드라마는 도전적이고 흥미진진한 플롯이었어. 리뷰 최대 세 줄까지 노출..."
            ),
            Top10Item(
                "어느 날 우리 집 현관으로 멸망이 들어왔다",
                "전체 4.2점",
                "ENFP 4.7점",
                "ESFJ / 피자치킨님",
                "이 드라마는 도전적이고 흥미진진한 플롯이었어."
            )
        )

        val viewPagerAdapter = Top10ViewPagerAdapter()
        viewPagerAdapter.submitList(dataList)
        binding.viewPagerTop10.adapter = viewPagerAdapter
    }
}
