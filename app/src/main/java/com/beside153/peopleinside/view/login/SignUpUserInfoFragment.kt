package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpUserInfoBinding

class SignUpUserInfoFragment : Fragment() {
    private lateinit var binding: FragmentSignUpUserInfoBinding

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

        binding.birthYearChoiceTextView.setOnClickListener {
            val bottomSheet = SignUpBottomSheetFragment(requireActivity())
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }

        binding.mbtiChoiceTextView.setOnClickListener {
            val action = SignUpUserInfoFragmentDirections.actionSignUpUserInfoFragmentToSignUpMbtiChoiceFragment()
            findNavController().navigate(action)
        }
    }
}
