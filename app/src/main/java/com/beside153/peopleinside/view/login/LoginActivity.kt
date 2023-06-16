package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityLoginBinding
import com.beside153.peopleinside.model.login.UserInfoModel
import com.beside153.peopleinside.util.showToast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val tag = javaClass.simpleName
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

    private fun onKakaoLoginClick() {
        if (kakaoApi.isKakaoTalkLoginAvailable(this)) {
            kakaoApi.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(tag, "$error")
                    if (error is AuthError && error.reason == AuthErrorCause.AccessDenied) { // 사용자가 취소
                        return@loginWithKakaoTalk
                    } else { // 다른 오류
                        showToast(R.string.kakaotalk_login_failed)
                        // 카카오 이메일 로그인
                        kakaoApi.loginWithKakaoAccount(this, callback = kakaoEmailLoginCallbak)
                    }
                } else if (token != null) {
                    getKakaoAccountInfo()
                }
            }
        } else {
            // 카카오 이메일 로그인
            kakaoApi.loginWithKakaoAccount(this, callback = kakaoEmailLoginCallbak)
        }
    }

    private val kakaoEmailLoginCallbak: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(tag, "$error")
            if (!(error is ClientError && error.reason == ClientErrorCause.Cancelled)) {
                showToast(R.string.kakao_email_login_failed)
            }
        } else if (token != null) {
            getKakaoAccountInfo()
        }
    }

    private fun getKakaoAccountInfo() {
        kakaoApi.me { user, error ->
            if (error != null) {
                Log.e(tag, "$error")
                showToast(R.string.kakao_user_info_load_failed)
            } else if (user != null) {
                val account = user.kakaoAccount
                val userInfo = UserInfoModel(
                    account?.profile?.nickname,
                    account?.email,
                    account?.gender,
                    account?.ageRange,
                    account?.birthday
                )
                startActivity(SignUpActivity.newIntent(this, userInfo))
                finish()
            }
        }
    }
}
