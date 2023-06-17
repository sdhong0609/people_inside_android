package com.beside153.peopleinside.view.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpTermsBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.viewmodel.login.SignUpTermsViewModel

class SignUpTermsFragment : Fragment() {
    private lateinit var binding: FragmentSignUpTermsBinding
    private val signUpTermsViewModel: SignUpTermsViewModel by viewModels()

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
            viewModel = signUpTermsViewModel
            lifecycleOwner = this@SignUpTermsFragment
        }

        signUpTermsViewModel.nextButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val action = SignUpTermsFragmentDirections.actionSignUpTermsFragmentToSignUpUserInfoFragment()
                findNavController().navigate(action)
            }
        )

        signUpTermsViewModel.seeTermsClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://www.naver.com")
                startActivity(intent)
            }
        )
    }
}
