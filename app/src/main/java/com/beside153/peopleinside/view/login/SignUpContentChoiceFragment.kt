package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpContentChoiceBinding
import com.beside153.peopleinside.model.login.ContentModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.GridSpacingItemDecoration
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.view.MainActivity
import com.beside153.peopleinside.view.login.ContentScreenAdapter.ContentScreenModel
import com.beside153.peopleinside.viewmodel.login.SignUpContentChoiceViewModel

class SignUpContentChoiceFragment : Fragment() {
    private lateinit var binding: FragmentSignUpContentChoiceBinding
    private val contentViewModel: SignUpContentChoiceViewModel by viewModels()
    private val contentAdapter = ContentScreenAdapter(::onContentItemClick)
    private var contentList = mutableListOf<ContentModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_content_choice, container, false)
        return binding.root
    }

    @Suppress("LongMethod")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = contentViewModel
            lifecycleOwner = this@SignUpContentChoiceFragment
        }

        contentList = mutableListOf(
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/ej7Br2B8dkZZBGa6vDE8HqATgU7.jpg",
                "블랙 미러",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2ts8XDLOTndAeb1Z7xdNoJX2PJG.jpg",
                "블랙 클로버: 마법제의 검",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lCUvpSvjAPU82HvJ8XfR74Chv5r.jpg",
                "그레이 아나토미",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9WF6TxCYwdiZw51NM92ConaQz1w.jpg",
                "존 윅 4",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wXNihLltMCGR7XepN39syIlCt5X.jpg",
                "분노의 질주: 라이드 오어 다이",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lCanGgsqF4xD2WA5NF8PWeT3IXd.jpg",
                "칸다하르",
                false
            )
        )

        val gridLayoutManager = GridLayoutManager(requireActivity(), COUNT_THREE)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (contentAdapter.getItemViewType(position)) {
                    R.layout.item_sign_up_content_choice_title -> COUNT_THREE
                    else -> COUNT_ONE
                }
            }
        }

        binding.contetChoiceScreenRecyclerView.apply {
            adapter = contentAdapter
            layoutManager = gridLayoutManager
            addItemDecoration(GridSpacingItemDecoration(16.dpToPx(resources.displayMetrics)))
        }
        contentAdapter.submitList(screenList())

        binding.chooseButton.setOnClickListener {
            startActivity(MainActivity.newIntent(requireActivity()))
            requireActivity().setOpenActivityAnimation()
            requireActivity().finish()
        }

        contentViewModel.backButtonClickEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigateUp()
            }
        )
    }

    private fun onContentItemClick(item: ContentModel) {
        val updatedList = contentList.map {
            if (it == item) {
                if (!it.isChosen) {
                    it.copy(isChosen = true)
                } else {
                    it.copy(isChosen = false)
                }
            } else {
                it
            }
        }

        contentList.clear()
        contentList.addAll(updatedList)
        contentAdapter.submitList(screenList())
    }

    @Suppress("SpreadOperator")
    private fun screenList(): List<ContentScreenModel> {
        return listOf(
            ContentScreenModel.TitleViewItem,
            *contentList.map { ContentScreenModel.ContentListItem(it) }.toTypedArray()
        )
    }

    companion object {
        private const val COUNT_ONE = 1
        private const val COUNT_THREE = 3
    }
}
