package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpUserInfoBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.viewmodel.login.SignUpUserInfoViewModel

class SignUpUserInfoFragment : Fragment() {
    private lateinit var binding: FragmentSignUpUserInfoBinding
    private var year = INITIAL_YEAR
    private val signUpUserInfoViewModel: SignUpUserInfoViewModel by viewModels()

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

        signUpUserInfoViewModel.setSelectedYear(INITIAL_YEAR)

        childFragmentManager.setFragmentResultListener(
            SignUpBottomSheetFragment::class.java.simpleName,
            this
        ) { _, bundle ->
            year = bundle.getInt(YEAR_KEY)
            signUpUserInfoViewModel.setSelectedYear(year)
        }

        binding.mbtiChoiceTextView.setOnClickListener {
            val action = SignUpUserInfoFragmentDirections.actionSignUpUserInfoFragmentToSignUpMbtiChoiceFragment()
            findNavController().navigate(action)
        }

        binding.signUpButton.setOnClickListener {
            val action = SignUpUserInfoFragmentDirections.actionSignUpUserInfoFragmentToSignUpContentChoiceFragment()
            findNavController().navigate(action)
        }

        signUpUserInfoViewModel.birthYearClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val bottomSheet = SignUpBottomSheetFragment.newInstance(year)
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        )
    }

    companion object {
        private const val INITIAL_YEAR = 1990
        private const val YEAR_KEY = "year"
    }
}
