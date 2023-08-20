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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseFragment
import com.beside153.peopleinside.common.extension.eventObserve
import com.beside153.peopleinside.databinding.FragmentSearchBinding
import com.beside153.peopleinside.model.mediacontent.SearchHotModel
import com.beside153.peopleinside.model.mediacontent.SearchedContentModel
import com.beside153.peopleinside.model.mediacontent.SearchingTitleModel
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.contentdetail.ContentDetailActivity
import com.beside153.peopleinside.viewmodel.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchScreenAdapter: SearchScreenAdapter
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        searchScreenAdapter = SearchScreenAdapter(
            ::onSearchingTitleItemClick,
            ::onSearchHotItemClick,
            ::onSearchedContentItemClick,
            searchViewModel
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.apply {
            viewModel = searchViewModel
            lifecycleOwner = this@SearchFragment
        }

        searchViewModel.initSearchScreen()

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

        searchViewModel.keyword.observe(viewLifecycleOwner) {
            binding.searchScreenRecyclerView.clearOnScrollListeners()
        }

        searchViewModel.backButtonClickEvent.eventObserve(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        searchViewModel.error.eventObserve(viewLifecycleOwner) {
            showErrorDialog(it) { searchViewModel.initSearchScreen() }
        }

        searchViewModel.searchCompleteEvent.eventObserve(viewLifecycleOwner) {
            inputMethodManager.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
            binding.searchScreenRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                    if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                        searchViewModel.loadMoreContentList()
                    }
                }
            })
        }
    }

    private fun onSearchingTitleItemClick(item: SearchingTitleModel) {
        searchViewModel.onSearchingTitleItemClick(item)
    }

    private fun onSearchHotItemClick(item: SearchHotModel) {
        startActivity(ContentDetailActivity.newIntent(requireActivity(), false, item.contentId))
        requireActivity().setOpenActivityAnimation()
    }

    private fun onSearchedContentItemClick(item: SearchedContentModel) {
        startActivity(ContentDetailActivity.newIntent(requireActivity(), false, item.contentId))
        requireActivity().setOpenActivityAnimation()
    }
}
