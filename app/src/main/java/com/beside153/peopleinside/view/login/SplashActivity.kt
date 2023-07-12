package com.beside153.peopleinside.view.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.view.MainActivity
import com.beside153.peopleinside.viewmodel.login.SplashViewmodel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private val splashViewmodel: SplashViewmodel by viewModels { SplashViewmodel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashViewmodel.getAllData()

        splashViewmodel.onBoardingCompletedEvent.observe(
            this,
            EventObserver { completed ->
                if (completed) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (App.prefs.getNickname().isEmpty()) {
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

        splashViewmodel.error.observe(
            this,
            EventObserver {
                showErrorDialog { splashViewmodel.getAllData() }
            }
        )
    }

    companion object {
        private const val SPLASH_DURATION = 1500L
        private const val ON_BOARDING = "on boarding not completed"
    }
}
