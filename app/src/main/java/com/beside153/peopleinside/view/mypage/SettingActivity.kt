package com.beside153.peopleinside.view.mypage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivitySettingBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.mypage.SettingViewModel

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val settingViewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)

        binding.apply {
            viewModel = settingViewModel
            lifecycleOwner = this@SettingActivity
        }

        addBackPressedCallback()

        settingViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )

        settingViewModel.termsClickEvent.observe(
            this,
            EventObserver {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://peopleinside.notion.site/1e270175949d4942b2e025d35107362e?pvs=4")
                startActivity(intent)
            }
        )

        settingViewModel.privacyPolicyClickEvent.observe(
            this,
            EventObserver {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://peopleinside.notion.site/1e270175949d4942b2e025d35107362e?pvs=4")
                startActivity(intent)
            }
        )

        settingViewModel.updateClickEvent.observe(
            this,
            EventObserver {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://play.google.com/store/apps/details?id=com.beside153.peopleinside")
                startActivity(intent)
            }
        )
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, SettingActivity::class.java)
        }
    }
}
