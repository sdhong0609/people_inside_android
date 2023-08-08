package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityCreatePostBinding
import com.beside153.peopleinside.model.community.MbtiTagModel
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.util.setCloseActivityAnimation
import com.beside153.peopleinside.view.dialog.TwoButtonsDialog
import com.beside153.peopleinside.viewmodel.community.CreatePostViewModel

class CreatePostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding
    private val createPostViewModel: CreatePostViewModel by viewModels()
    private val mbtiTagAdapter = MbtiTagListAdapter(::onMbtiTagItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_post)

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showStopPostDialog()
                }
            }
        )

        binding.apply {
            viewModel = createPostViewModel
            lifecycleOwner = this@CreatePostActivity
        }

        binding.mbtiTagRecyclerView.apply {
            adapter = mbtiTagAdapter
            layoutManager = GridLayoutManager(this@CreatePostActivity, 4)
        }

        createPostViewModel.initMbtiTagList()

        createPostViewModel.mbtiTagList.observe(this) { list ->
            mbtiTagAdapter.submitList(list)
        }

        createPostViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                showStopPostDialog()
            }
        )
    }

    private fun onMbtiTagItemClick(item: MbtiTagModel) {
        createPostViewModel.onMbtiTagItemClick(item)
    }

    private fun showStopPostDialog() {
        val stopPostDialog = TwoButtonsDialog.TwoButtonsDialogBuilder()
            .setTitle(R.string.stop_post_dialog_title)
            .setDescription(R.string.stop_post_dialog_description)
            .setYesText(R.string.stop_post_dialog_yes)
            .setNoText(R.string.stop_post_dialog_no)
            .setButtonClickListener(object : TwoButtonsDialog.TwoButtonsDialogListener {
                override fun onClickPositiveButton() {
                    finish()
                    setCloseActivityAnimation()
                }

                override fun onClickNegativeButton() = Unit
            }).create()
        stopPostDialog.show(supportFragmentManager, stopPostDialog.tag)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, CreatePostActivity::class.java)
    }
}
