package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpMbtiChoiceBinding
import com.beside153.peopleinside.model.login.MbtiModel

class SignUpMbtiChoiceFragment : Fragment() {
    private lateinit var binding: FragmentSignUpMbtiChoiceBinding
    private val mbtiAdapter = MbtiChoiceListAdapter(::onMbtiItemClick)

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

        val mbtiList = listOf(
            MbtiModel(R.drawable.mbti_large_img_infp, "INFP", true),
            MbtiModel(R.drawable.mbti_large_img_enfp, "ENFP", true),
            MbtiModel(R.drawable.mbti_large_img_enfj, "ENFJ", true),
            MbtiModel(R.drawable.mbti_large_img_isfj, "ISFJ", true)
        )

        binding.mbtiChoiceRecyclerView.apply {
            adapter = mbtiAdapter
            layoutManager = GridLayoutManager(requireActivity(), SPAN_COUNT)
        }
        mbtiAdapter.submitList(mbtiList)
    }

    @Suppress("UnusedPrivateMember")
    private fun onMbtiItemClick(item: MbtiModel) {
        //
    }

    companion object {
        private const val SPAN_COUNT = 3
    }
}
