package com.beside153.peopleinside.view.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpBottomSheetBinding
import com.beside153.peopleinside.model.common.BirthYearModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BirthYearBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSignUpBottomSheetBinding
    private val yearListAdapter = BirthYearListAdapter(::onYearItemClick)
    private var yearList = mutableListOf<BirthYearModel>()
    private var selectedYearItem: BirthYearModel? = null
    private var selectedIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedYear = arguments?.getInt(YEAR_KEY) ?: FIRST_YEAR
        selectedIndex = selectedYear - FIRST_YEAR

        (0 until MAX_YEAR_COUNT).forEach { index ->
            val year = FIRST_YEAR + index
            yearList.add(BirthYearModel(year, year == selectedYear))
        }

        binding.birthYearRecyclerView.apply {
            adapter = yearListAdapter
            layoutManager = LinearLayoutManager(context)
            scrollToPosition(selectedIndex)
        }
        yearListAdapter.submitList(yearList.toList())

        binding.confirmButton.setOnClickListener {
            setFragmentResult(
                BirthYearBottomSheetFragment::class.java.simpleName,
                bundleOf(YEAR_KEY to (selectedYearItem?.year ?: FIRST_YEAR))
            )
            dismiss()
        }
    }

    private fun onYearItemClick(item: BirthYearModel) {
        val updatedList = yearList.map {
            if (it == item) {
                it.copy(isChosen = true)
            } else {
                it.copy(isChosen = false)
            }
        }

        yearList.clear()
        yearList.addAll(updatedList)
        yearListAdapter.submitList(updatedList)
        selectedYearItem = item
    }

    companion object {
        private const val MAX_YEAR_COUNT = 50
        private const val FIRST_YEAR = 1964
        private const val YEAR_KEY = "year"

        fun newInstance(year: Int) = BirthYearBottomSheetFragment().apply {
            arguments = bundleOf(YEAR_KEY to year)
        }
    }
}
