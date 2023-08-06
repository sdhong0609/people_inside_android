package com.beside153.peopleinside.view.community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityCommunitySearchBinding
import com.beside153.peopleinside.util.EventObserver
import com.beside153.peopleinside.viewmodel.community.CommunitySearchViewModel

class CommunitySearchActivity : BaseActivity() {
    private lateinit var binding: ActivityCommunitySearchBinding
    private val communitySearchViewModel: CommunitySearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_search)

        binding.apply {
            viewModel = communitySearchViewModel
            lifecycleOwner = this@CommunitySearchActivity
        }

        communitySearchViewModel.error.observe(
            this,
            EventObserver {
                //
            }
        )

        communitySearchViewModel.backButtonClickEvent.observe(
            this,
            EventObserver {
                finish()
            }
        )
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, CommunitySearchActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            return intent
        }
    }
}
