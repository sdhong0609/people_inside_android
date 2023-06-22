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
        searchViewModel.initSearchScreen()

        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.searchEditText, InputMethodManager.SHOW_IMPLICIT)

        binding.searchScreenRecyclerView.apply {
            adapter = searchScreenAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setOnTouchListener { v, _ ->
                v.performClick()
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

        searchViewModel.screenList.observe(viewLifecycleOwner) { list ->
            searchScreenAdapter.submitList(list)
        }

        searchViewModel.backButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigateUp()
            }
        )

        searchViewModel.keyword.observe(viewLifecycleOwner) {
            searchViewModel.loadSearchingTitle()
        }
    }

    private fun onSearchTrendItemClick(item: SearchTrendItem) {
        Toast.makeText(requireActivity(), item.contentTitle, Toast.LENGTH_SHORT).show()
    }
}
