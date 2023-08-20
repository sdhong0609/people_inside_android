package com.beside153.peopleinside.view.onboarding.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseFragment
import com.beside153.peopleinside.common.extension.eventObserve
import com.beside153.peopleinside.databinding.FragmentSignUpTermsBinding
import com.beside153.peopleinside.viewmodel.onboarding.signup.SignUpTermsViewModel

class SignUpTermsFragment : BaseFragment() {
    private lateinit var binding: FragmentSignUpTermsBinding
    private val termsViewModel: SignUpTermsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_terms, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = termsViewModel
            lifecycleOwner = this@SignUpTermsFragment
        }

        termsViewModel.nextButtonClickEvent.eventObserve(viewLifecycleOwner) {
            val action = SignUpTermsFragmentDirections.actionSignUpTermsFragmentToSignUpUserInfoFragment()
            findNavController().navigate(action)
        }

        termsViewModel.seeTermsClickEvent.eventObserve(viewLifecycleOwner) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://peopleinside.notion.site/ac6615474dcb40749f59ab453527a602?")
            startActivity(intent)
        }

        termsViewModel.seePrivacyPolicyClickEvent.eventObserve(viewLifecycleOwner) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://peopleinside.notion.site/1e270175949d4942b2e025d35107362e?pvs=4")
            startActivity(intent)
        }
    }
}
