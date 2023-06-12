package com.beside153.peopleinside.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityOnBoardingBinding
import com.beside153.peopleinside.model.login.UserInfoModel

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding)
    }

    companion object {
        private const val USER_INFO = "USER_INFO"

        fun onBoardingIntent(context: Context, userInfo: UserInfoModel): Intent {
            val intent = Intent(context, OnBoardingActivity::class.java)
            intent.putExtra(USER_INFO, userInfo)
            return intent
        }
    }
}
