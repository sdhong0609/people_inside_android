package com.beside153.peopleinside.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityNonMemberMbtiChoiceBinding
import com.beside153.peopleinside.model.login.MbtiModel
import com.beside153.peopleinside.util.GridSpacingItemDecoration
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.MainActivity
import com.beside153.peopleinside.view.login.NonMemberMbtiScreenAdapter.NonMemberMbtiScreenModel

class NonMemberMbtiChoiceActivity : BaseActivity() {
    private lateinit var binding: ActivityNonMemberMbtiChoiceBinding
    private val mbtiAdapter = NonMemberMbtiScreenAdapter(::onMbtiItemClick)
    private var mbtiList = mutableListOf<MbtiModel>()
    private var selectedMbtiItem: MbtiModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_non_member_mbti_choice)

        addBackPressedCallback()

        mbtiList = mutableListOf(
            MbtiModel(R.drawable.mbti_large_img_infp, "INFP", false),
            MbtiModel(R.drawable.mbti_large_img_enfp, "ENFP", false),
            MbtiModel(R.drawable.mbti_large_img_esfj, "ESFJ", false),
            MbtiModel(R.drawable.mbti_large_img_isfj, "ISFJ", false),
            MbtiModel(R.drawable.mbti_large_img_isfp, "ISFP", false),
            MbtiModel(R.drawable.mbti_large_img_esfp, "ESFP", false),
            MbtiModel(R.drawable.mbti_large_img_intp, "INTP", false),
            MbtiModel(R.drawable.mbti_large_img_infj, "INFJ", false),
            MbtiModel(R.drawable.mbti_large_img_enfj, "ENFJ", false),
            MbtiModel(R.drawable.mbti_large_img_entp, "ENTP", false),
            MbtiModel(R.drawable.mbti_large_img_estj, "ESTJ", false),
            MbtiModel(R.drawable.mbti_large_img_istj, "ISTJ", false),
            MbtiModel(R.drawable.mbti_large_img_intj, "INTJ", false),
            MbtiModel(R.drawable.mbti_large_img_istp, "ISTP", false),
            MbtiModel(R.drawable.mbti_large_img_estp, "ESTP", false),
            MbtiModel(R.drawable.mbti_large_img_entj, "ENTJ", false)
        )

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
        mbtiAdapter.submitList(screenList())

        binding.backImageButton.setOnClickListener {
            finish()
            setCloseActivityAnimation()
        }

        binding.completeChooseButton.setOnClickListener {
            App.prefs.setString(App.prefs.jwtTokenKey, getString(R.string.nonmember_jwt_token))
            App.prefs.setUserId(1)
            App.prefs.setNickname(getString(R.string.nonmember_nickname))
            App.prefs.setMbti(selectedMbtiItem?.mbtiText ?: "INFP")
            App.prefs.setIsMember(false)

            startActivity(MainActivity.newIntent(this, false))
            finishAffinity()
            setOpenActivityAnimation()
        }
    }

    private fun onMbtiItemClick(item: MbtiModel) {
        val updatedList = mbtiList.map {
            if (it == item) {
                it.copy(isChosen = true)
            } else {
                it.copy(isChosen = false)
            }
        }

        mbtiList.clear()
        mbtiList.addAll(updatedList)
        mbtiAdapter.submitList(screenList())
        selectedMbtiItem = item
    }

    @Suppress("SpreadOperator")
    private fun screenList(): List<NonMemberMbtiScreenModel> {
        return listOf(
            NonMemberMbtiScreenModel.TitleViewItem,
            *mbtiList.map { NonMemberMbtiScreenModel.MbtiListItem(it) }.toTypedArray()
        )
    }

    companion object {
        private const val COUNT_ONE = 1
        private const val COUNT_THREE = 3

        fun newIntent(context: Context): Intent {
            return Intent(context, NonMemberMbtiChoiceActivity::class.java)
        }
    }
}
