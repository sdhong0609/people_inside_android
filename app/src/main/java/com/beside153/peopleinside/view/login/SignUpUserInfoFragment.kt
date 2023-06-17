package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpUserInfoBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.viewmodel.login.SignUpUserInfoViewModel

class SignUpUserInfoFragment : Fragment() {
    private lateinit var binding: FragmentSignUpUserInfoBinding
    private val signUpUserInfoViewModel: SignUpUserInfoViewModel by viewModels()
    private var year = INITIAL_YEAR
    private var mbti = INITIAL_MBTI
    private var gender = INITIAL_GENDER

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_user_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = signUpUserInfoViewModel
            lifecycleOwner = this@SignUpUserInfoFragment
        }

        signUpUserInfoViewModel.setSelectedYear(year)
        signUpUserInfoViewModel.setSelectedMbti(mbti)
        signUpUserInfoViewModel.setSelectedGender(gender)

        childFragmentManager.setFragmentResultListener(
            SignUpBottomSheetFragment::class.java.simpleName,
            this
        ) { _, bundle ->
            year = bundle.getInt(YEAR_KEY)
            signUpUserInfoViewModel.setSelectedYear(year)
        }

        setFragmentResultListener(SignUpMbtiChoiceFragment::class.java.simpleName) { _, bundle ->
            mbti = bundle.getString(MBTI_KEY) ?: INITIAL_MBTI
            signUpUserInfoViewModel.setSelectedMbti(mbti)
        }

        signUpUserInfoViewModel.birthYearClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val bottomSheet = SignUpBottomSheetFragment.newInstance(year)
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        )

        signUpUserInfoViewModel.mbtiChoiceClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val action =
                    SignUpUserInfoFragmentDirections.actionSignUpUserInfoFragmentToSignUpMbtiChoiceFragment(mbti)
                findNavController().navigate(action)
            }
        )

        binding.signUpButton.setOnClickListener {
            val action = SignUpUserInfoFragmentDirections.actionSignUpUserInfoFragmentToSignUpContentChoiceFragment()
            findNavController().navigate(action)
        }
    }

    companion object {
        private const val YEAR_KEY = "year"
        private const val INITIAL_YEAR = 1990
        private const val MBTI_KEY = "mbti"
        private const val INITIAL_MBTI = "선택"
        private const val INITIAL_GENDER = "여자"
    }
}
