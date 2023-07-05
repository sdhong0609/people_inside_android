package com.beside153.peopleinside.view.mypage

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseFragment
import com.beside153.peopleinside.databinding.FragmentEditProfileBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.mypage.EditProfileViewModel

class EditProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = editProfileViewModel
            lifecycleOwner = this@EditProfileFragment
        }

        editProfileViewModel.completeButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                requireActivity().finish()
                requireActivity().setResult(RESULT_OK)
                requireActivity().setCloseActivityAnimation()
            }
        )
    }
}
