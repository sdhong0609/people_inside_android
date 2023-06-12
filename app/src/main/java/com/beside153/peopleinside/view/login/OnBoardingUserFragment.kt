package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentOnBoardingUserBinding

class OnBoardingUserFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_boarding_user, container, false)
        return binding.root
    }
}
