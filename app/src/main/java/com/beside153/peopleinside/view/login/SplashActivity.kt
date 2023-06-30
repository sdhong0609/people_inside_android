package com.beside153.peopleinside.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.beside153.peopleinside.App
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.service.RetrofitClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private val exceptionHandler = CoroutineExceptionHandler { _, t ->
        Timber.e(t.message)
        showErrorDialog { getReportList() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, SPLASH_DURATION)

        getReportList()
    }

    private fun getReportList() {
        lifecycleScope.launch(exceptionHandler) {
            val reportList = RetrofitClient.reportService.getReportList()
            App.prefs.setString(App.prefs.reportListKey, Json.encodeToString(reportList))
        }
    }

    companion object {
        private const val SPLASH_DURATION = 1500L
    }
}
