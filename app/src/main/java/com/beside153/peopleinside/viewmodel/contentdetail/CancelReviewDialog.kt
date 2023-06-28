package com.beside153.peopleinside.viewmodel.contentdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.DialogCancelReviewBinding

class CancelReviewDialog : DialogFragment() {
    private lateinit var binding: DialogCancelReviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_cancel_review, container, false)
        return binding.root
    }
}
