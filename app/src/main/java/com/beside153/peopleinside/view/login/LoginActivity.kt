package com.beside153.peopleinside.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityLoginBinding
import com.beside153.peopleinside.util.showToast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        kakaoApi = UserApiClient.instance

        binding.kakaoLoginButton.setOnClickListener {
            onKakaoLoginClick()
        }
    }

    private val didUserCancel: (error: Throwable) -> Boolean = { error ->
        error is ClientError && error.reason == ClientErrorCause.Cancelled ||
            error is AuthError && error.msg == "UserInfo denied access"
    }

    private fun onKakaoLoginClick() {
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
        kakaoApi.me { user, error ->
            if (error != null) {
                Timber.e(error)
                showToast(R.string.kakao_user_info_load_failed)
            } else if (user != null) {
                App.prefs.setEmail(user.kakaoAccount?.email ?: "")
                startActivity(SignUpActivity.newIntent(this, authToken))
                finish()
            }
        }
    }
}
