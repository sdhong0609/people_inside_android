package com.beside153.peopleinside.view.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpBottomSheetBinding
import com.beside153.peopleinside.model.login.BirthYearModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SignUpBottomSheetFragment(private val context: Context) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSignUpBottomSheetBinding
    private val yearListAdapter = SignUpYearListAdapter(::onYearItemClick)
    private var yearList = mutableListOf<BirthYearModel>()

    // ViewModel 공유해서 값을 공유할 예정
    private var initialIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("MagicNumber")
        for (i in 0..49) {
            if (i == initialIndex) {
                yearList.add(BirthYearModel(i, true))
                continue
            }
            yearList.add(BirthYearModel(i, false))
        }

        binding.birthYearRecyclerView.apply {
            adapter = yearListAdapter
            layoutManager = LinearLayoutManager(this@SignUpBottomSheetFragment.context)
        }
        yearListAdapter.submitList(yearList.toList())

        binding.confirmButton.setOnClickListener {
            dismiss()
        }
    }

    private fun onYearItemClick(item: BirthYearModel) {
        yearList[initialIndex] = BirthYearModel(initialIndex, false)
        yearList[item.index] = BirthYearModel(item.index, true)
        yearListAdapter.submitList(yearList.toList())
        initialIndex = item.index
    }
}
