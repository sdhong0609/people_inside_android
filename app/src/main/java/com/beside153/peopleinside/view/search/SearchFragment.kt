package com.beside153.peopleinside.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSearchBinding
import com.beside153.peopleinside.model.search.SearchTrendItem

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchScreenAdapter = SearchScreenAdapter(::onSearchTrendItemClick)

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

        binding.searchAppbar.searchEditText.apply {
            setHorizontallyScrolling(true)
            requestFocus()
        }

        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.searchAppbar.searchEditText, InputMethodManager.SHOW_IMPLICIT)

        binding.searchAppbar.backImageButton.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToRecommendFragment()
            findNavController().navigate(action)
        }

        binding.searchAppbar.searchCancelImageView.setOnClickListener {
            binding.searchAppbar.searchEditText.text?.clear()
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

        binding.searchRecyclerView.apply {
            adapter = searchScreenAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setOnTouchListener { v, _ ->
                v.performClick()
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

        @Suppress("SpreadOperator")
        val list = listOf(
            SearchScreenAdapter.SearchScreenModel.SeenViewItem,
            SearchScreenAdapter.SearchScreenModel.TrendViewItem,
            *searchTrendList.map { SearchScreenAdapter.SearchScreenModel.TrendContentItem(it) }.toTypedArray()
        )
        searchScreenAdapter.submitList(list)
    }

    private fun onSearchTrendItemClick(item: SearchTrendItem) {
        Toast.makeText(requireActivity(), item.contentTitle, Toast.LENGTH_SHORT).show()
    }
}
