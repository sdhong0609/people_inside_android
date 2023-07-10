package com.beside153.peopleinside.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityNonMemberMbtiChoiceBinding
import com.beside153.peopleinside.util.addBackPressedCallback

class NonMemberMbtiChoiceActivity : BaseActivity() {
    private lateinit var binding: ActivityNonMemberMbtiChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_non_member_mbti_choice)

        addBackPressedCallback()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, NonMemberMbtiChoiceActivity::class.java)
        }
    }
}
