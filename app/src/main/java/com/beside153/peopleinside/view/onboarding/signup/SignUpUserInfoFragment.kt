package com.beside153.peopleinside.view.onboarding.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseFragment
import com.beside153.peopleinside.databinding.FragmentSignUpUserInfoBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.view.common.BirthYearBottomSheetFragment
import com.beside153.peopleinside.viewmodel.onboarding.signup.SignUpUserInfoViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpUserInfoFragment : BaseFragment() {
    private lateinit var binding: FragmentSignUpUserInfoBinding
    private val userInfoViewModel: SignUpUserInfoViewModel by activityViewModels()
    private var year = INITIAL_YEAR
    private var mbti = INITIAL_MBTI
    private var gender = INITIAL_GENDER

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        userInfoViewModel.initNickname()
    }

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
            viewModel = userInfoViewModel
            lifecycleOwner = this@SignUpUserInfoFragment
        }

        initSelectedValues()
        setFragmentsResultListener()

        userInfoViewModel.selectedGender.observe(viewLifecycleOwner) {
            gender = it
        }

        userInfoViewModel.birthYearClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val bottomSheet = BirthYearBottomSheetFragment.newInstance(year)
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        )

        userInfoViewModel.mbtiChoiceClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val action =
                    SignUpUserInfoFragmentDirections.actionSignUpUserInfoFragmentToSignUpMbtiChoiceFragment(mbti)
                findNavController().navigate(action)
            }
        )

        userInfoViewModel.signUpButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                firebaseAnalytics.logEvent("회원가입") {
                    param("유저_ID", App.prefs.getUserId().toString())
                    param("유저명", App.prefs.getNickname())
                    param("유저_MBTI", App.prefs.getMbti())
                    param("소셜로그인_경로", "KAKAO")
                }

                val action =
                    SignUpUserInfoFragmentDirections.actionSignUpUserInfoFragmentToSignUpContentChoiceFragment()
                findNavController().navigate(action)
            }
        )

        userInfoViewModel.backButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigateUp()
            }
        )

        userInfoViewModel.error.observe(
            viewLifecycleOwner,
            EventObserver {
                showErrorDialog(it) { userInfoViewModel.onSignUpButtonClick() }
            }
        )
    }

    private fun setFragmentsResultListener() {
        childFragmentManager.setFragmentResultListener(
            BirthYearBottomSheetFragment::class.java.simpleName,
            this
        ) { _, bundle ->
            year = bundle.getInt(YEAR_KEY)
            userInfoViewModel.setSelectedYear(year)
        }

        setFragmentResultListener(SignUpMbtiChoiceFragment::class.java.simpleName) { _, bundle ->
            mbti = bundle.getString(MBTI_KEY) ?: INITIAL_MBTI
            userInfoViewModel.setSelectedMbti(mbti)
        }
    }

    private fun initSelectedValues() {
        userInfoViewModel.setSelectedYear(year)
        userInfoViewModel.setSelectedMbti(mbti)
        userInfoViewModel.setSelectedGender(gender)
    }

    companion object {
        private const val YEAR_KEY = "year"
        private const val INITIAL_YEAR = 1990
        private const val MBTI_KEY = "mbti"
        private const val INITIAL_MBTI = "선택"
        private const val INITIAL_GENDER = "women"
    }
}
