package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpMbtiChoiceBinding
import com.beside153.peopleinside.model.login.MbtiModel
import com.beside153.peopleinside.util.GridSpacingItemDecoration
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.view.login.MbtiScreenAdapter.MbtiScreenModel

class SignUpMbtiChoiceFragment : Fragment() {
    private lateinit var binding: FragmentSignUpMbtiChoiceBinding
    private val mbtiAdapter = MbtiScreenAdapter(::onMbtiItemClick)
    private var mbtiList = mutableListOf<MbtiModel>()
    private var selectedMbtiItem: MbtiModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_mbti_choice, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mbtiList = mutableListOf(
            MbtiModel(R.drawable.mbti_large_img_infp, "INFP", false),
            MbtiModel(R.drawable.mbti_large_img_enfp, "ENFP", false),
            MbtiModel(R.drawable.mbti_large_img_esfj, "ENFJ", false),
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
            addItemDecoration(GridSpacingItemDecoration(16.dpToPx(resources.displayMetrics)))
        }
        mbtiAdapter.submitList(screenList())

        binding.completeChooseButton.setOnClickListener {
            setFragmentResult(
                SignUpMbtiChoiceFragment::class.java.simpleName,
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
            MbtiScreenModel.TitleViewItem,
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
