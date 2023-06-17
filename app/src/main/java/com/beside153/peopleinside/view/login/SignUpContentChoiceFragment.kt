package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpContentChoiceBinding
import com.beside153.peopleinside.model.login.ContentModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.GridSpacingItemDecoration
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.MainActivity
import com.beside153.peopleinside.viewmodel.login.SignUpContentChoiceViewModel

class SignUpContentChoiceFragment : Fragment() {
    private lateinit var binding: FragmentSignUpContentChoiceBinding
    private val contentViewModel: SignUpContentChoiceViewModel by viewModels()
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
            lifecycleOwner = this@SignUpContentChoiceFragment
        }

        contentViewModel.initContentList()

        initScreenRecyclerView()
        contentAdapter.submitList(contentViewModel.screenList())

        contentViewModel.contentItemClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                contentAdapter.submitList(contentViewModel.screenList())
            }
        )

        contentViewModel.completeButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                startActivity(MainActivity.newIntent(requireActivity()))
                requireActivity().setOpenActivityAnimation()
                requireActivity().finish()
            }
        )

        contentViewModel.backButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigateUp()
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
            addItemDecoration(GridSpacingItemDecoration(16.dpToPx(resources.displayMetrics)))
        }
    }

    private fun onContentItemClick(item: ContentModel) {
        contentViewModel.onContentItemClick(item)
    }

    companion object {
        private const val COUNT_ONE = 1
        private const val COUNT_THREE = 3
    }
}
