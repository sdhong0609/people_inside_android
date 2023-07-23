package com.beside153.peopleinside.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityLoginBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.view.MainActivity
import com.beside153.peopleinside.view.login.nonmember.NonMemberMbtiChoiceActivity
import com.beside153.peopleinside.view.onboarding.signup.SignUpActivity
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

        // 비회원일 때 뒤로가기 설정
        if (App.prefs.getNickname() == getString(R.string.nonmember_nickname)) {
            addBackPressedCallback { setResult(BACK_FROM_LOGINACTIVITY) }

            loginViewModel.backButtonClickEvent.observe(
                this,
                EventObserver {
                    setResult(BACK_FROM_LOGINACTIVITY)
                    finish()
                    setCloseActivityAnimation()
                }
            )
        }

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
                finishAffinity()
            }
        )

        loginViewModel.onBoardingCompletedEvent.observe(
            this,
            EventObserver { completed ->
                if (completed) {
                    startActivity(MainActivity.newIntent(this, false))
                } else {
                    startActivity(SignUpActivity.newIntent(this, ON_BOARDING))
                }
                finishAffinity()
            }
        )

        loginViewModel.withoutLoginClickEvent.observe(
            this,
            EventObserver {
                startActivity(NonMemberMbtiChoiceActivity.newIntent(this))
                setOpenActivityAnimation()
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

    companion object {
        private const val BACK_FROM_LOGINACTIVITY = 111
        private const val ON_BOARDING = "on boarding not completed"

        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}
