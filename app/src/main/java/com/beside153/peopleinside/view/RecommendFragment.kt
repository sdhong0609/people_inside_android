package com.beside153.peopleinside.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentRecommendBinding
import com.beside153.peopleinside.model.Top10Item
import com.beside153.peopleinside.service.RetrofitClient.mbtiService
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

        binding.recommendPick10Layout.viewPagerTop10.adapter = pagerAdapter

        loadTop10ItemList("esfj")
    }

    private fun loadTop10ItemList(mbti: String) {
        val call = mbtiService.getTop10Content(mbti)
        call.enqueue(object : Callback<List<Top10Item>> {
            override fun onResponse(call: Call<List<Top10Item>>, response: Response<List<Top10Item>>) {
                top10ItemList = response.body()!!
                pagerAdapter.submitList(top10ItemList)
            }

            override fun onFailure(call: Call<List<Top10Item>>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
