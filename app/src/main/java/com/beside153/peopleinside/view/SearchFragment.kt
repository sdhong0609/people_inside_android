package com.beside153.peopleinside.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSearchBinding
import com.beside153.peopleinside.model.search.SearchTrendItem

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchTrendAdpater = SearchTrendListAdapter(::onSearchTrendItemClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchAppbar.editTextSearch.setHorizontallyScrolling(true)

        val activity = activity as MainActivity
        val navHostFragment =
            activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.searchAppbar.buttonBack.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToRecommendFragment()
            navController.navigate(action)
        }

        binding.searchAppbar.imageViewSearchCancel.setOnClickListener {
            binding.searchAppbar.editTextSearch.text?.clear()
        }

        val searchTrendList = listOf(
            SearchTrendItem("1", "분노의 질주: 라이드 오어 다이"),
            SearchTrendItem("2", "가디언즈 오브 갤럭시: Volume 3"),
            SearchTrendItem("3", "분노의 질주: 더 얼티메이트"),
            SearchTrendItem("4", "분노의 질주: 라이드 오어 다이"),
            SearchTrendItem("5", "앤트맨과 와스프: 퀀텀매니아"),
            SearchTrendItem("6", "가디언즈 오브 갤럭시: Volume 3"),
            SearchTrendItem("7", "분노의 질주: 라이드 오어 다이"),
            SearchTrendItem("8", "분노의 질주: 라이드 오어 다이"),
            SearchTrendItem("9", "분노의 질주: 라이드 오어 다이"),
            SearchTrendItem("10", "분노의 질주: 라이드 오어 다이")
        )

        binding.recyclerVIewContentsTrend.apply {
            adapter = searchTrendAdpater
            layoutManager = object : LinearLayoutManager(requireActivity()) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
        }

        searchTrendAdpater.submitList(searchTrendList)
    }

    private fun onSearchTrendItemClick(item: SearchTrendItem) {
        Toast.makeText(requireActivity(), item.contentTitle, Toast.LENGTH_SHORT).show()
    }
}
