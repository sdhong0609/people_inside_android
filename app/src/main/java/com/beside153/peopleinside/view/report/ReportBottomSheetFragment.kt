package com.beside153.peopleinside.view.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentReportBottomSheetBinding
import com.beside153.peopleinside.model.report.ReportModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReportBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentReportBottomSheetBinding
    private val reportAdapter = ReportListAdapter(::onReportItemClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reportRecyclerView.apply {
            adapter = reportAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    @Suppress("UnusedPrivateMember")
    private fun onReportItemClick(item: ReportModel) {
        //
    }
}
