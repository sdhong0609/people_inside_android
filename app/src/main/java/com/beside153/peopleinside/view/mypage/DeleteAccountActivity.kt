package com.beside153.peopleinside.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityDeleteAccountBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.view.dialog.TwoButtonsDialog
import com.beside153.peopleinside.view.login.LoginActivity
import com.beside153.peopleinside.viewmodel.mypage.DeleteAccountViewModel
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import timber.log.Timber

class DeleteAccountActivity : BaseActivity() {
    private lateinit var binding: ActivityDeleteAccountBinding
    private val deleteAccountViewModel: DeleteAccountViewModel by viewModels { DeleteAccountViewModel.Factory }
    private lateinit var kakaoApi: UserApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delete_account)

        binding.apply {
            viewModel = deleteAccountViewModel
            lifecycleOwner = this@DeleteAccountActivity
        }

        addBackPressedCallback()

        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        kakaoApi = UserApiClient.instance

        deleteAccountViewModel.initReasonList()

        deleteAccountViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )

        deleteAccountViewModel.deleteAccountClickEvent.observe(
            this,
            EventObserver {
                val dialog = TwoButtonsDialog.TwoButtonsDialogBuilder()
                    .setTitle(R.string.delete_account_dialog_title)
                    .setDescription(R.string.delete_account_dialog_description)
                    .setButtonClickListener(object : TwoButtonsDialog.TwoButtonsDialogListener {
                        override fun onClickPositiveButton() {
                            deleteAccountViewModel.deleteAccount()
                        }

                        override fun onClickNegativeButton() {
                            // 필요없음
                        }
                    }).create()
                dialog.show(supportFragmentManager, dialog.tag)
            }
        )

        deleteAccountViewModel.deleteAccountSuccessEvent.observe(
            this,
            EventObserver {
                unlinkKakaoAccount()
                startActivity(LoginActivity.newIntent(this))
                finishAffinity()
            }
        )

        deleteAccountViewModel.error.observe(
            this,
            EventObserver {
                showErrorDialog { deleteAccountViewModel.deleteAccount() }
            }
        )

        deleteAccountViewModel.withDrawalReasonList.observe(this) { list ->
            //
        }
    }

    private fun unlinkKakaoAccount() {
        kakaoApi.unlink { error ->
            if (error != null) {
                Timber.e(error, "연결 끊기 실패")
            } else {
                Timber.i("연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, DeleteAccountActivity::class.java)
        }
    }
}
