package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpContentChoiceBinding

class SignUpContentChoiceFragment : Fragment() {
    private lateinit var binding: FragmentSignUpContentChoiceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_content_choice, container, false)
        return binding.root
    }
}
