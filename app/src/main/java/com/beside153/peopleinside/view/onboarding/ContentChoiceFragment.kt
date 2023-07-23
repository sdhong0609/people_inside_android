package com.beside153.peopleinside.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpContentChoiceBinding
import com.beside153.peopleinside.model.mediacontent.OnBoardingContentModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.MainActivity
import com.beside153.peopleinside.viewmodel.login.SignUpContentChoiceViewModel

class ContentChoiceFragment : Fragment() {
    private lateinit var binding: FragmentSignUpContentChoiceBinding
    private val contentViewModel: SignUpContentChoiceViewModel by viewModels { SignUpContentChoiceViewModel.Factory }
    private val contentAdapter = ContentScreenAdapter(::onContentItemClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_content_choice, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = contentViewModel
            lifecycleOwner = this@ContentChoiceFragment
        }

        contentViewModel.initAllData()

        initScreenRecyclerView()
        contentViewModel.screenList.observe(viewLifecycleOwner) { list ->
            contentAdapter.submitList(list)
        }

        contentViewModel.completeButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                startActivity(MainActivity.newIntent(requireActivity(), true))
                requireActivity().setOpenActivityAnimation()
                requireActivity().finish()
            }
        )

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }

    private fun initScreenRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireActivity(), COUNT_THREE)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (contentAdapter.getItemViewType(position)) {
                    R.layout.item_sign_up_content_choice_title -> COUNT_THREE
                    else -> COUNT_ONE
                }
            }
        }

        binding.contetChoiceScreenRecyclerView.apply {
            adapter = contentAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                    if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                        contentViewModel.loadMoreData()
                    }
                }
            })
        }
    }

    private fun onContentItemClick(item: OnBoardingContentModel) {
        contentViewModel.onContentItemClick(item)
    }

    companion object {
        private const val COUNT_ONE = 1
        private const val COUNT_THREE = 3
    }
}
