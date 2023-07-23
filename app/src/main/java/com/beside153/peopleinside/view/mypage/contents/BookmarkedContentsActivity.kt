package com.beside153.peopleinside.view.mypage.contents

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityMypageBookmarkContentsBinding
import com.beside153.peopleinside.model.mycontent.BookmarkedContentModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.viewmodel.mypage.BookmarkedContentsViewModel

class BookmarkedContentsActivity : BaseActivity() {
    private lateinit var binding: ActivityMypageBookmarkContentsBinding
    private val contentsAdapter = BookmarkedContentListAdapter(::onBookmarkClick)
    private val contentsViewModel: BookmarkedContentsViewModel by viewModels { BookmarkedContentsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_bookmark_contents)

        addBackPressedCallback { setResult(RESULT_OK) }

        contentsViewModel.initAllData()

        binding.apply {
            viewModel = contentsViewModel
            lifecycleOwner = this@BookmarkedContentsActivity
        }

        binding.bookmarkContentsRecyclerView.apply {
            adapter = contentsAdapter
            layoutManager = GridLayoutManager(this@BookmarkedContentsActivity, SPAN_COUNT)
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

        contentsViewModel.bookmarkCreatedEvent.observe(
            this,
            EventObserver { bookmarkCreated ->
                if (bookmarkCreated) {
                    showToast(R.string.bookmark_created)
                    return@EventObserver
                }
                showToast(R.string.bookmark_deleted)
            }
        )

        contentsViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                setResult(RESULT_OK)
                finish()
                setCloseActivityAnimation()
            }
        )

        contentsViewModel.error.observe(
            this,
            EventObserver {
                contentsViewModel.initAllData()
            }
        )
    }

    private fun onBookmarkClick(item: BookmarkedContentModel) {
        contentsViewModel.onBookmarkClick(item)
    }

    companion object {
        private const val SPAN_COUNT = 3

        fun newIntent(context: Context): Intent {
            return Intent(context, BookmarkedContentsActivity::class.java)
        }
    }
}
