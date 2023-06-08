package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.OnBoardingUserFragmentBinding

class OnBoardingUserFragment : Fragment() {
    private lateinit var binding: OnBoardingUserFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.on_boarding_user_fragment, container, false)
        return binding.root
    }
}
