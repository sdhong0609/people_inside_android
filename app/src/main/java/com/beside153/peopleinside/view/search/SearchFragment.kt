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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSearchBinding
import com.beside153.peopleinside.model.search.SearchTrendItem
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.viewmodel.search.SearchViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    @Suppress("UnusedPrivateMember")
    private val searchViewModel: SearchViewModel by viewModels(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(RetrofitClient.searchService) as T
                }
            }
        }
    )
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

        binding.apply {
            viewModel = searchViewModel
            lifecycleOwner = this@SearchFragment
        }

        binding.searchEditText.requestFocus()

        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.searchEditText, InputMethodManager.SHOW_IMPLICIT)

        @Suppress("MagicNumber")
        val searchTrendList = listOf(
            SearchTrendItem(1, "1", "분노의 질주: 라이드 오어 다이"),
            SearchTrendItem(2, "2", "가디언즈 오브 갤럭시: Volume 3"),
            SearchTrendItem(3, "3", "분노의 질주: 더 얼티메이트"),
            SearchTrendItem(4, "4", "분노의 질주: 라이드 오어 다이"),
            SearchTrendItem(5, "5", "앤트맨과 와스프: 퀀텀매니아"),
            SearchTrendItem(6, "6", "가디언즈 오브 갤럭시: Volume 3"),
            SearchTrendItem(7, "7", "분노의 질주: 라이드 오어 다이"),
            SearchTrendItem(8, "8", "분노의 질주: 라이드 오어 다이"),
            SearchTrendItem(9, "9", "분노의 질주: 라이드 오어 다이"),
            SearchTrendItem(10, "10", "분노의 질주: 라이드 오어 다이")
        )

        binding.searchScreenRecyclerView.apply {
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

        searchViewModel.backButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigateUp()
            }
        )

        searchViewModel.searchCancelClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                binding.searchEditText.setText("")
            }
        )
    }

    private fun onSearchTrendItemClick(item: SearchTrendItem) {
        Toast.makeText(requireActivity(), item.contentTitle, Toast.LENGTH_SHORT).show()
    }
}
