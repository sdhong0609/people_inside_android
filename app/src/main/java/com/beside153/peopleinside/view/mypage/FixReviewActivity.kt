package com.beside153.peopleinside.view.mypage

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityFixReviewBinding
import com.beside153.peopleinside.view.commonview.CancelReviewDialogInterface

class FixReviewActivity : BaseActivity(), CancelReviewDialogInterface {
    private lateinit var binding: ActivityFixReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fix_review)
    }

    override fun onDialogYesButtonClick() {
        TODO("Not yet implemented")
    }
}
