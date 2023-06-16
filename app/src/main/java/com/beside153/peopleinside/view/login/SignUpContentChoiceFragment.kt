package com.beside153.peopleinside.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.FragmentSignUpContentChoiceBinding
import com.beside153.peopleinside.model.login.ContentModel
import com.beside153.peopleinside.util.GridSpacingItemDecoration
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.view.login.ContentScreenAdapter.ContentScreenModel

class SignUpContentChoiceFragment : Fragment() {
    private lateinit var binding: FragmentSignUpContentChoiceBinding
    private val contentAdapter = ContentScreenAdapter(::onContentItemClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_content_choice, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contentList = listOf(
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
                false
            ),
            ContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
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

        @Suppress("SpreadOperator")
        val list = listOf(
            ContentScreenModel.TitleViewItem,
            *contentList.map { ContentScreenModel.ContentListItem(it) }.toTypedArray()
        )
        contentAdapter.submitList(list)
    }

    @Suppress("UnusedPrivateMember")
    private fun onContentItemClick(item: ContentModel) {
        //
    }

    companion object {
        private const val COUNT_ONE = 1
        private const val COUNT_THREE = 3
    }
}
