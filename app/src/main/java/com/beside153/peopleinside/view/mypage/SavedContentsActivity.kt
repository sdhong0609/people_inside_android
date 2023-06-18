package com.beside153.peopleinside.view.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityMypageSavedContentsBinding

class SavedContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMypageSavedContentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage_saved_contents)
    }
}
