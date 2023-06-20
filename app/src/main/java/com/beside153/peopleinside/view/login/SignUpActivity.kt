package com.beside153.peopleinside.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivitySignUpBinding
import com.beside153.peopleinside.viewmodel.login.SignUpUserInfoViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val userInfoViewModel: SignUpUserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        userInfoViewModel.setAuthToken(intent.getStringExtra(AUTH_TOKEN) ?: "")
    }

    companion object {
        private const val AUTH_TOKEN = "AUTH_TOKEN"

        fun newIntent(context: Context, authToken: String): Intent {
            val intent = Intent(context, SignUpActivity::class.java)
            intent.putExtra(AUTH_TOKEN, authToken)
            return intent
        }
    }
}
