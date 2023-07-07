package com.beside153.peopleinside.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityDeleteAccountBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.mypage.DeleteAccountViewModel

class DeleteAccountActivity : BaseActivity() {
    private lateinit var binding: ActivityDeleteAccountBinding
    private val deleteAccountViewModel: DeleteAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delete_account)

        binding.apply {
            viewModel = deleteAccountViewModel
            lifecycleOwner = this@DeleteAccountActivity
        }

        addBackPressedCallback()

        deleteAccountViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, DeleteAccountActivity::class.java)
        }
    }
}
