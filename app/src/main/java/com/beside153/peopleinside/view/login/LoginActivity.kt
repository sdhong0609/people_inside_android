package com.beside153.peopleinside.view.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityLoginBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.view.MainActivity
import com.beside153.peopleinside.viewmodel.login.LoginViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var kakaoApi: UserApiClient
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.apply {
            viewModel = loginViewModel
            lifecycleOwner = this@LoginActivity
        }

        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        kakaoApi = UserApiClient.instance

        loginViewModel.kakaoLoginClickEvent.observe(
            this,
            EventObserver {
                kakaoLogin()
            }
        )

        loginViewModel.goToSignUpEvent.observe(
            this,
            EventObserver { authToken ->
                startActivity(SignUpActivity.newIntent(this, authToken))
                finish()
            }
        )

        loginViewModel.loginSuccessEvent.observe(
            this,
            EventObserver {
                startActivity(MainActivity.newIntent(this, false))
                finish()
            }
        )
    }

    private val didUserCancel: (error: Throwable) -> Boolean = { error ->
        error is ClientError && error.reason == ClientErrorCause.Cancelled ||
            error is AuthError && error.msg == "UserInfo denied access"
    }

    private fun kakaoLogin() {
        if (kakaoApi.isKakaoTalkLoginAvailable(this)) {
            kakaoApi.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Timber.e(error)
                    if (!didUserCancel(error)) {
                        showToast(R.string.kakaotalk_login_failed)
                        // 카카오 이메일 로그인
                        kakaoApi.loginWithKakaoAccount(this, callback = kakaoEmailLoginCallbak)
                    }
                } else if (token != null) {
                    getKakaoAccountInfo(token.accessToken)
                }
            }
        } else {
            // 카카오 이메일 로그인
            kakaoApi.loginWithKakaoAccount(this, callback = kakaoEmailLoginCallbak)
        }
    }

    private val kakaoEmailLoginCallbak: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e(error)
            if (!didUserCancel(error)) {
                showToast(R.string.kakao_email_login_failed)
            }
        } else if (token != null) {
            getKakaoAccountInfo(token.accessToken)
        }
    }

    private fun getKakaoAccountInfo(authToken: String) {
        loginViewModel.setAuthToken(authToken)

        kakaoApi.me { user, error ->
            if (error != null) {
                Timber.e(error)
                showToast(R.string.kakao_user_info_load_failed)
            } else if (user != null) {
                App.prefs.setEmail(user.kakaoAccount?.email ?: "")
                loginViewModel.peopleInsideLogin()
            }
        }
    }
}
