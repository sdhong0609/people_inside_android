package com.beside153.peopleinside.view.mypage.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseFragment
import com.beside153.peopleinside.databinding.FragmentMyPageMbtiChoiceBinding
import com.beside153.peopleinside.model.common.MbtiModel
import com.beside153.peopleinside.view.onboarding.signup.MbtiScreenAdapter
import com.beside153.peopleinside.view.onboarding.signup.MbtiScreenAdapter.MbtiScreenModel

class EditProfileMbtiChoiceFragment : BaseFragment() {
    private lateinit var binding: FragmentMyPageMbtiChoiceBinding
    private val mbtiAdapter = MbtiScreenAdapter(::onMbtiItemClick)
    private var mbtiList = mutableListOf<MbtiModel>()
    private var selectedMbtiItem: MbtiModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_page_mbti_choice, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: EditProfileMbtiChoiceFragmentArgs by navArgs()
        val mbti = args.mbti

        mbtiList = mutableListOf(
            MbtiModel(R.drawable.mbti_large_img_infp, "INFP", mbti == "INFP"),
            MbtiModel(R.drawable.mbti_large_img_enfp, "ENFP", mbti == "ENFP"),
            MbtiModel(R.drawable.mbti_large_img_esfj, "ESFJ", mbti == "ESFJ"),
            MbtiModel(R.drawable.mbti_large_img_isfj, "ISFJ", mbti == "ISFJ"),
            MbtiModel(R.drawable.mbti_large_img_isfp, "ISFP", mbti == "ISFP"),
            MbtiModel(R.drawable.mbti_large_img_esfp, "ESFP", mbti == "ESFP"),
            MbtiModel(R.drawable.mbti_large_img_intp, "INTP", mbti == "INTP"),
            MbtiModel(R.drawable.mbti_large_img_infj, "INFJ", mbti == "INFJ"),
            MbtiModel(R.drawable.mbti_large_img_enfj, "ENFJ", mbti == "ENFJ"),
            MbtiModel(R.drawable.mbti_large_img_entp, "ENTP", mbti == "ENTP"),
            MbtiModel(R.drawable.mbti_large_img_estj, "ESTJ", mbti == "ESTJ"),
            MbtiModel(R.drawable.mbti_large_img_istj, "ISTJ", mbti == "ISTJ"),
            MbtiModel(R.drawable.mbti_large_img_intj, "INTJ", mbti == "INTJ"),
            MbtiModel(R.drawable.mbti_large_img_istp, "ISTP", mbti == "ISTP"),
            MbtiModel(R.drawable.mbti_large_img_estp, "ESTP", mbti == "ESTP"),
            MbtiModel(R.drawable.mbti_large_img_entj, "ENTJ", mbti == "ENTJ")
        )

        mbtiList.forEach {
            if (mbti == it.mbtiText) {
                selectedMbtiItem = it
            }
        }

        val gridLayoutManager = GridLayoutManager(requireActivity(), COUNT_THREE)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mbtiAdapter.getItemViewType(position)) {
                    R.layout.item_sign_up_mbti_title -> COUNT_THREE
                    else -> COUNT_ONE
                }
            }
        }

        binding.mbtiScreenRecyclerView.apply {
            adapter = mbtiAdapter
            layoutManager = gridLayoutManager

            when (selectedMbtiItem?.mbtiText) {
                "ENTP", "ESTJ", "ISTJ", "INTJ", "ISTP", "ESTP", "ENTJ" -> scrollToPosition(mbtiList.size - 1)
            }
        }
        mbtiAdapter.submitList(screenList())

        binding.backImageButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.completeChooseButton.setOnClickListener {
            setFragmentResult(
                EditProfileMbtiChoiceFragment::class.java.simpleName,
                bundleOf(MBTI_KEY to (selectedMbtiItem?.mbtiText ?: INITIAL_MBTI))
            )
            findNavController().navigateUp()
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
    private fun screenList(): List<MbtiScreenModel> {
        return listOf(
            *mbtiList.map { MbtiScreenModel.MbtiListItem(it) }.toTypedArray()
        )
    }

    companion object {
        private const val COUNT_ONE = 1
        private const val COUNT_THREE = 3
        private const val MBTI_KEY = "mbti"
        private const val INITIAL_MBTI = "선택"
    }
}
