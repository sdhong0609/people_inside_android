package com.beside153.peopleinside.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityMypageSavedContentsBinding
import com.beside153.peopleinside.model.mypage.SavedContentModel
import com.beside153.peopleinside.util.GridSpacingItemDecoration
import com.beside153.peopleinside.util.addBackPressedCallback
import com.beside153.peopleinside.util.dpToPx
import com.beside153.peopleinside.util.setCloseActivityAnimation

class SavedContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageSavedContentsBinding
    private val contentsAdapter = SavedContentsListAdapter(::onContentItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_saved_contents)

        addBackPressedCallback()

        binding.backImageButton.setOnClickListener {
            finish()
            setCloseActivityAnimation()
        }

        val contentList = listOf(
            SavedContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/voddFVdjUoAtfoZZp2RUmuZILDI.jpg",
                "스파이더맨: 노웨이 홈",
                true
            ),
            SavedContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/ej7Br2B8dkZZBGa6vDE8HqATgU7.jpg",
                "블랙 미러",
                true
            ),
            SavedContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2ts8XDLOTndAeb1Z7xdNoJX2PJG.jpg",
                "블랙 클로버: 마법제의 검",
                true
            ),
            SavedContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lCUvpSvjAPU82HvJ8XfR74Chv5r.jpg",
                "그레이 아나토미",
                true
            ),
            SavedContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9WF6TxCYwdiZw51NM92ConaQz1w.jpg",
                "존 윅 4",
                true
            ),
            SavedContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wXNihLltMCGR7XepN39syIlCt5X.jpg",
                "분노의 질주: 라이드 오어 다이",
                true
            ),
            SavedContentModel(
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lCanGgsqF4xD2WA5NF8PWeT3IXd.jpg",
                "칸다하르",
                true
            )
        )

        binding.savedContentsRecyclerView.apply {
            adapter = contentsAdapter
            layoutManager = GridLayoutManager(this@SavedContentsActivity, SPAN_COUNT)
            addItemDecoration(GridSpacingItemDecoration(16.dpToPx(resources.displayMetrics)))
        }
        contentsAdapter.submitList(contentList)
    }

    @Suppress("UnusedPrivateMember")
    private fun onContentItemClick(item: SavedContentModel) {
        //
    }

    companion object {
        private const val SPAN_COUNT = 3

        fun newIntent(context: Context): Intent {
            return Intent(context, SavedContentsActivity::class.java)
        }
    }
}
