package com.beside153.peopleinside.view.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentBottomSheetBinding
import com.beside153.peopleinside.model.report.ReportModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.serialization.json.Json

enum class BottomSheetType {
    ContentReport,
    PostFixDelete,
    PostReport,
    CommentReport
}

class BottomSheetFragment(private val bottomSheetType: BottomSheetType) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private val reportAdapter = BottomSheetListAdapter(::onReportItemClick)

    override fun getTheme(): Int = R.style.CustomBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomSheetRecyclerView.apply {
            adapter = reportAdapter
            layoutManager = LinearLayoutManager(context)
        }

        if (bottomSheetType == BottomSheetType.ContentReport) {
            val reportList = Json.decodeFromString<List<ReportModel>>(App.prefs.getString(App.prefs.reportListKey))
            reportAdapter.submitList(reportList.map { BottomSheetListAdapter.BottomSheetModel.ReportItem(it) })
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun onReportItemClick(item: ReportModel) {
        setFragmentResult(
            BottomSheetFragment::class.java.simpleName,
            bundleOf(REPORT_ID to item.id)
        )
        dismiss()
    }

    companion object {
        private const val REPORT_ID = "REPORT_ID"
    }
}
