package com.beside153.peopleinside.view.login.nonmember

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityNonMemberMbtiChoiceBinding
import com.beside153.peopleinside.model.common.MbtiModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.GridSpacingItemDecoration
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.MainActivity
import com.beside153.peopleinside.viewmodel.login.nonmember.NonMemberMbtiChoiceViewModel

class NonMemberMbtiChoiceActivity : BaseActivity() {
    private lateinit var binding: ActivityNonMemberMbtiChoiceBinding
    private val mbtiAdapter = NonMemberMbtiScreenAdapter(::onMbtiItemClick)
    private val mbtiChoiceViewmodel: NonMemberMbtiChoiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_non_member_mbti_choice)

        binding.apply {
            viewModel = mbtiChoiceViewmodel
            lifecycleOwner = this@NonMemberMbtiChoiceActivity
        }

        addBackPressedCallback()
        initRecyclerView()

        mbtiChoiceViewmodel.initAllData()

        mbtiChoiceViewmodel.screenList.observe(this) { list ->
            mbtiAdapter.submitList(list)
        }

        mbtiChoiceViewmodel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
                setCloseActivityAnimation()
            }
        )

        mbtiChoiceViewmodel.completeButtonClickEvent.observe(
            this,
            EventObserver { mbti ->
                App.prefs.setString(App.prefs.jwtTokenKey, getString(R.string.nonmember_jwt_token))
                App.prefs.setUserId(1)
                App.prefs.setNickname(getString(R.string.nonmember_nickname))
                App.prefs.setMbti(mbti.uppercase())
                App.prefs.setIsMember(false)

                startActivity(MainActivity.newIntent(this, false))
                finishAffinity()
                setOpenActivityAnimation()
            }
        )
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, COUNT_THREE)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mbtiAdapter.getItemViewType(position)) {
                    R.layout.item_non_member_mbti_title -> COUNT_THREE
                    else -> COUNT_ONE
                }
            }
        }

        binding.mbtiScreenRecyclerView.apply {
            adapter = mbtiAdapter
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpacingItemDecoration(16.dpToPx(resources.displayMetrics)))
        }
    }

    private fun onMbtiItemClick(item: MbtiModel) {
        mbtiChoiceViewmodel.onMbtiItemClick(item)
    }

    companion object {
        private const val COUNT_ONE = 1
        private const val COUNT_THREE = 3

        fun newIntent(context: Context): Intent {
            return Intent(context, NonMemberMbtiChoiceActivity::class.java)
        }
    }
}
