package com.beside153.peopleinside.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.view.MainActivity
import com.beside153.peopleinside.view.dialog.ErrorDialog
import com.beside153.peopleinside.view.onboarding.signup.SignUpActivity
import com.beside153.peopleinside.viewmodel.login.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()
    }

    override fun onResume() {
        super.onResume()
        splashViewModel.getAllData()
    }

    private fun initObservers() {
        splashViewModel.onBoardingCompletedEvent.observe(
            this,
            EventObserver { completed ->
                if (completed) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (App.prefs.getUserId() == 0 || App.prefs.getNickname().isEmpty()) {
                            startActivity(LoginActivity.newIntent(this))
                            finish()
                            return@postDelayed
                        }
                        startActivity(MainActivity.newIntent(this, false))
                        finish()
                    }, SPLASH_DURATION)
                    return@EventObserver
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(SignUpActivity.newIntent(this, ON_BOARDING))
                    finish()
                }, SPLASH_DURATION)
            }
        )

        splashViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog(it) { splashViewModel.getAllData() }
            }
        )

        splashViewModel.updateAppEvent.observe(
            this,
            EventObserver {
                val needUpdateDialog = ErrorDialog.ErrorDialogBuilder()
                    .setTitleRes(R.string.need_update_dialog_title)
                    .setDescriptionRes(R.string.need_update_dialog_description)
                    .setButtonTextRes(R.string.need_update_dialog_button)
                    .setButtonClickListener(object : ErrorDialog.ErrorDialogListener {
                        override fun onClickRefreshButton() {
                            splashViewModel.onGoToPlayStoreButtonClick()
                        }
                    }).create()
                needUpdateDialog.isCancelable = false
                needUpdateDialog.show(supportFragmentManager, needUpdateDialog.tag)
            }
        )

        splashViewModel.goToPlayStoreEvent.observe(
            this,
            EventObserver {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://play.google.com/store/apps/details?id=com.beside153.peopleinside")
                startActivity(intent)
            }
        )
    }

    companion object {
        private const val SPLASH_DURATION = 1500L
        private const val ON_BOARDING = "on boarding not completed"
    }
}
