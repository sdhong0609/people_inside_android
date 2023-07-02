package com.beside153.peopleinside.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityMypageBookmarkContentsBinding
import com.beside153.peopleinside.model.mypage.BookmarkedContentModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.GridSpacingItemDecoration
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.viewmodel.mypage.BookmarkedContentsViewModel

class BookmarkContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageBookmarkContentsBinding
    private val contentsAdapter = BookmarkContentsListAdapter(::onBookmarkClick)
    private val contentsViewModel: BookmarkedContentsViewModel by viewModels { BookmarkedContentsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_bookmark_contents)

        addBackPressedCallback { setResult(RESULT_OK) }

        contentsViewModel.initAllData()

        binding.apply {
            viewModel = contentsViewModel
            lifecycleOwner = this@BookmarkContentsActivity
        }

        binding.savedContentsRecyclerView.apply {
            adapter = contentsAdapter
            layoutManager = GridLayoutManager(this@BookmarkContentsActivity, SPAN_COUNT)
            addItemDecoration(GridSpacingItemDecoration(16.dpToPx(resources.displayMetrics)))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                    if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                        contentsViewModel.loadMoreData()
                    }
                }
            })
        }

        contentsViewModel.contentList.observe(this) { list ->
            contentsAdapter.submitList(list)
        }

        contentsViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                setResult(RESULT_OK)
                finish()
                setCloseActivityAnimation()
            }
        )
    }

    private fun onBookmarkClick(item: BookmarkedContentModel) {
        contentsViewModel.onBookmarkClick(item)
    }

    companion object {
        private const val SPAN_COUNT = 3

        fun newIntent(context: Context): Intent {
            return Intent(context, BookmarkContentsActivity::class.java)
        }
    }
}
