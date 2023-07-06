package com.beside153.peopleinside.view.mypage

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseFragment
import com.beside153.peopleinside.databinding.FragmentEditProfileBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.view.login.SignUpBottomSheetFragment
import com.beside153.peopleinside.view.login.SignUpMbtiChoiceFragment
import com.beside153.peopleinside.viewmodel.mypage.EditProfileViewModel

class EditProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by activityViewModels { EditProfileViewModel.Factory }
    private var year = App.prefs.getBirth().toInt()
    private var mbti = App.prefs.getMbti()
    private var gender = App.prefs.getGender()

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

        setFragmentsResultListener()

        editProfileViewModel.initAllData()

        editProfileViewModel.selectedGender.observe(viewLifecycleOwner) {
            gender = it
        }

        editProfileViewModel.birthYearClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val bottomSheet = SignUpBottomSheetFragment.newInstance(year)
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        )

        editProfileViewModel.completeButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                requireActivity().finish()
                requireActivity().setResult(RESULT_OK)
                requireActivity().setCloseActivityAnimation()
            }
        )

        editProfileViewModel.nickname.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                editProfileViewModel.setNicknameIsEmpty(false)
            }
        }

        editProfileViewModel.error.observe(
            viewLifecycleOwner,
            EventObserver {
                showErrorDialog { editProfileViewModel.initAllData() }
            }
        )
    }

    private fun setFragmentsResultListener() {
        childFragmentManager.setFragmentResultListener(
            SignUpBottomSheetFragment::class.java.simpleName,
            this
        ) { _, bundle ->
            year = bundle.getInt(YEAR_KEY)
            editProfileViewModel.setSelectedYear(year)
        }

        setFragmentResultListener(SignUpMbtiChoiceFragment::class.java.simpleName) { _, bundle ->
            mbti = bundle.getString(MBTI_KEY) ?: INITIAL_MBTI
            editProfileViewModel.setSelectedMbti(mbti)
        }
    }

    companion object {
        private const val YEAR_KEY = "year"
        private const val MBTI_KEY = "mbti"
        private const val INITIAL_MBTI = "선택"
    }
}
