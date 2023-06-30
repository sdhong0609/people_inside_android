package com.beside153.peopleinside.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.beside153.peopleinside.util.EventObserver

open class BaseFragment : Fragment() {

    private val baseViewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseViewModel.error.observe(
            viewLifecycleOwner,
            EventObserver {
                // 다이얼로그 띄우기!!!!!
            }
        )
    }
}
