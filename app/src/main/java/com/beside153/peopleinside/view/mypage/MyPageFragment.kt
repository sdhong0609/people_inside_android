package com.beside153.peopleinside.view.mypage

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseFragment
import com.beside153.peopleinside.common.extension.eventObserve
import com.beside153.peopleinside.databinding.FragmentMyPageBinding
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.view.mypage.contents.BookmarkedContentsActivity
import com.beside153.peopleinside.view.mypage.contents.RatedContentsActivity
import com.beside153.peopleinside.view.mypage.editprofile.EditProfileActivity
import com.beside153.peopleinside.view.mypage.setting.SettingActivity
import com.beside153.peopleinside.viewmodel.mypage.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment() {
    private lateinit var binding: FragmentMyPageBinding
    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_page, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = myPageViewModel
            lifecycleOwner = this@MyPageFragment
        }

        myPageViewModel.initAllData()

        myPageViewModel.error.eventObserve(viewLifecycleOwner) {
            showErrorDialog(it) { myPageViewModel.initAllData() }
        }

        myPageViewModel.bookmarkContentsClickEvent.eventObserve(viewLifecycleOwner) {
            bookmarkContentsActivityLauncher.launch(BookmarkedContentsActivity.newIntent(requireActivity()))
            requireActivity().setOpenActivityAnimation()
        }

        myPageViewModel.ratingContentsClickEvent.eventObserve(viewLifecycleOwner) {
            ratingContentsActivityLauncher.launch(RatedContentsActivity.newIntent(requireActivity()))
            requireActivity().setOpenActivityAnimation()
        }

        myPageViewModel.editProfileClickEvent.eventObserve(viewLifecycleOwner) {
            editProfileActivityLauncher.launch(EditProfileActivity.newIntent(requireActivity()))
            requireActivity().setOpenActivityAnimation()
        }

        myPageViewModel.settingClickEvent.eventObserve(viewLifecycleOwner) {
            startActivity(SettingActivity.newIntent(requireActivity()))
            requireActivity().setOpenActivityAnimation()
        }
    }

    private val bookmarkContentsActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                myPageViewModel.initAllData()
            }
        }

    private val ratingContentsActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                myPageViewModel.initAllData()
            }
        }

    private val editProfileActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                myPageViewModel.initAllData()
                requireActivity().showToast(R.string.complete_edit_profile)
            }
        }
}
