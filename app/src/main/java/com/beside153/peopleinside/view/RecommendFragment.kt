package com.beside153.peopleinside.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.MarginPageTransformer
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentRecommendBinding
import com.beside153.peopleinside.model.Top10Item
import com.beside153.peopleinside.service.RetrofitClient.mbtiService
import com.beside153.peopleinside.util.dpToPx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendFragment : Fragment() {
    private lateinit var binding: FragmentRecommendBinding
    private lateinit var top10ItemList: List<Top10Item>
    private val pagerAdapter = Top10ViewPagerAdapter()

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

        binding.recommendPick10Layout.viewPagerTop10.apply {
            val pagerOffsetPx = 16.dpToPx(resources.displayMetrics)
            val pagerMarginPx = 8.dpToPx(resources.displayMetrics)
            adapter = pagerAdapter
            offscreenPageLimit = 2
            setPadding(pagerOffsetPx, 0, pagerOffsetPx, 0)
            setPageTransformer(MarginPageTransformer(pagerMarginPx))
        }

        loadTop10ItemList("esfj")
    }

    private fun loadTop10ItemList(mbti: String) {
        val call = mbtiService.getTop10Content(mbti)
        call.enqueue(object : Callback<List<Top10Item>> {
            override fun onResponse(call: Call<List<Top10Item>>, response: Response<List<Top10Item>>) {
                if (!response.isSuccessful || response.body() == null) {
                    Toast.makeText(requireActivity(), "데이터 불러오기를 실패했습니다", Toast.LENGTH_SHORT).show()
                    return
                }
                top10ItemList = response.body()!!
                pagerAdapter.submitList(top10ItemList)
            }

            override fun onFailure(call: Call<List<Top10Item>>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
