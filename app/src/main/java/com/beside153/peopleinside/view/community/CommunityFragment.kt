package com.beside153.peopleinside.view.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseFragment
import com.beside153.peopleinside.databinding.FragmentCommunityBinding
import com.beside153.peopleinside.model.community.post.CommunityPostModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.viewmodel.community.CommunityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment : BaseFragment() {
    private lateinit var binding: FragmentCommunityBinding
    private val communityViewModel: CommunityViewModel by viewModels()
    private val postListAdapter = CommunityPostListAdapter(::onPostItemClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_community, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = communityViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.communityPostRecyclerView.apply {
            adapter = postListAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        communityViewModel.initData()

        communityViewModel.postList.observe(viewLifecycleOwner) { list ->
            postListAdapter.submitList(list)
        }

        communityViewModel.error.observe(
            viewLifecycleOwner,
            EventObserver {
                //
            }
        )

        communityViewModel.searchBarClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                startActivity(CommunitySearchActivity.newIntent(requireActivity()))
            }
        )

        communityViewModel.writePostClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                writePostActivityLauncher.launch(CreatePostActivity.newIntent(requireActivity()))
                requireActivity().setOpenActivityAnimation()
            }
        )
    }

    private fun onPostItemClick(item: CommunityPostModel) {
//        communityViewModel.onPostItemClick(item)
    }

    private val writePostActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
//                communityViewModel.initAllData()
            }
        }
}
