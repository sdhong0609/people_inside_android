package com.beside153.peopleinside.view.common

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
import com.beside153.peopleinside.view.common.BottomSheetListAdapter.BottomSheetModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.serialization.json.Json

enum class BottomSheetType {
    ReviewReport,
    PostFixDelete,
    PostReport,
    CommentFixDelete,
    CommentReport
}

class BottomSheetFragment(private val bottomSheetType: BottomSheetType) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private val listAdapter = BottomSheetListAdapter(
        ::onReportItemClick,
        ::onPostFixDeleteClick,
        ::onCommentFixDeleteClick
    )

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
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        if (bottomSheetType == BottomSheetType.ReviewReport) {
            val reportList = Json.decodeFromString<List<ReportModel>>(App.prefs.getReportList())
                .filter { it.type == "MEDIA_CONTENT_REVIEW" }
            listAdapter.submitList(reportList.map { BottomSheetModel.ReportItem(it) })
            return
        }

        if (bottomSheetType == BottomSheetType.PostReport) {
            val reportList = Json.decodeFromString<List<ReportModel>>(App.prefs.getReportList())
                .filter { it.type == "COMMUNITY_POST" }
            listAdapter.submitList(reportList.map { BottomSheetModel.ReportItem(it) })
            return
        }

        if (bottomSheetType == BottomSheetType.CommentReport) {
            val reportList = Json.decodeFromString<List<ReportModel>>(App.prefs.getReportList())
                .filter { it.type == "COMMUNITY_COMMENT" }
            listAdapter.submitList(reportList.map { BottomSheetModel.ReportItem(it) })
            return
        }

        if (bottomSheetType == BottomSheetType.PostFixDelete) {
            val fixDeleteList = listOf(getString(R.string.fix), getString(R.string.delete))
            listAdapter.submitList(fixDeleteList.map { BottomSheetModel.PostFixDeleteItem(it) })
            return
        }

        if (bottomSheetType == BottomSheetType.CommentFixDelete) {
            val fixDeleteList = listOf(getString(R.string.fix), getString(R.string.delete))
            listAdapter.submitList(fixDeleteList.map { BottomSheetModel.CommentFixDeleteItem(it) })
            return
        }
    }

    private fun onReportItemClick(item: ReportModel) {
        when (bottomSheetType) {
            BottomSheetType.ReviewReport -> setFragmentResult(
                BottomSheetType.ReviewReport.name,
                bundleOf(BottomSheetType.ReviewReport.name to item.id)
            )

            BottomSheetType.PostReport -> setFragmentResult(
                BottomSheetType.PostReport.name,
                bundleOf(BottomSheetType.PostReport.name to item.id)
            )

            BottomSheetType.CommentReport -> setFragmentResult(
                BottomSheetType.CommentReport.name,
                bundleOf(BottomSheetType.CommentReport.name to item.id)
            )

            else -> throw IllegalArgumentException("Invalid bottom sheet type")
        }
        dismiss()
    }

    private fun onPostFixDeleteClick(item: String) {
        setFragmentResult(
            BottomSheetType.PostFixDelete.name,
            bundleOf(BottomSheetType.PostFixDelete.name to item)
        )
        dismiss()
    }

    private fun onCommentFixDeleteClick(item: String) {
        setFragmentResult(
            BottomSheetType.CommentFixDelete.name,
            bundleOf(BottomSheetType.CommentFixDelete.name to item)
        )
        dismiss()
    }
}
