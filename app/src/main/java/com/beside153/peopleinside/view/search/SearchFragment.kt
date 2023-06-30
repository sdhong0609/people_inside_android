package com.beside153.peopleinside.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseFragment
import com.beside153.peopleinside.databinding.FragmentSearchBinding
import com.beside153.peopleinside.model.search.SearchHotModel
import com.beside153.peopleinside.model.search.SearchingTitleModel
import com.beside153.peopleinside.service.RetrofitClient
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.contentdetail.ContentDetailActivity
import com.beside153.peopleinside.viewmodel.search.SearchViewModel

class SearchFragment : BaseFragment() {
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
    private lateinit var searchScreenAdapter: SearchScreenAdapter
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        searchScreenAdapter = SearchScreenAdapter(::onSearchingTitleItemClick, ::onSearchHotItemClick, searchViewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.apply {
            viewModel = searchViewModel
            lifecycleOwner = this@SearchFragment
        }

        binding.searchEditText.requestFocus()
        searchViewModel.initSearchScreen()

        inputMethodManager.showSoftInput(binding.searchEditText, InputMethodManager.SHOW_IMPLICIT)

        binding.searchScreenRecyclerView.apply {
            adapter = searchScreenAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            setOnTouchListener { v, _ ->
                v.performClick()
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchViewModel.searchContentAction()
                true
            } else {
                false
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

        searchViewModel.hideKeyboard.observe(
            viewLifecycleOwner,
            EventObserver {
                inputMethodManager.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
            }
        )

        searchViewModel.error.observe(
            viewLifecycleOwner,
            EventObserver {
                showErrorDialog { searchViewModel.initSearchScreen() }
            }
        )
    }

    private fun onSearchingTitleItemClick(item: SearchingTitleModel) {
        searchViewModel.onSearchingTitleItemClick(item)
    }

    private fun onSearchHotItemClick(item: SearchHotModel) {
        startActivity(ContentDetailActivity.newIntent(requireActivity(), false, item.id))
        requireActivity().setOpenActivityAnimation()
    }
}
