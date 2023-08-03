package com.beside153.peopleinside.view.onboarding.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivitySignUpBinding
import com.beside153.peopleinside.view.onboarding.ContentChoiceFragment
import com.beside153.peopleinside.viewmodel.onboarding.signup.SignUpUserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val userInfoViewModel: SignUpUserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        val authToken = intent.getStringExtra(AUTH_TOKEN)
        if (authToken == ON_BOARDING) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.signUpFragmentContainer, ContentChoiceFragment()).commit()
            return
        }
        userInfoViewModel.setAuthToken(authToken ?: "")
    }

    companion object {
        private const val AUTH_TOKEN = "AUTH_TOKEN"
        private const val ON_BOARDING = "on boarding not completed"

        fun newIntent(context: Context, authToken: String): Intent {
            val intent = Intent(context, SignUpActivity::class.java)
            intent.putExtra(AUTH_TOKEN, authToken)
            return intent
        }
    }
}
