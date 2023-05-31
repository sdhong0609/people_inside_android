package com.beside153.peopleinside.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setFragment(RecommendFragment())

        binding.bottomNavigationView.apply {
            setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.bottom_menu_recommend -> setFragment(RecommendFragment())
                    R.id.bottom_menu_search -> setFragment(SearchFragment())
                    R.id.bottom_menu_community -> setFragment(CommunityFragment())
                    R.id.bottom_menu_my_page -> setFragment(MyPageFragment())
                }
                true
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.containerFragmentMain.id, fragment).commit()
    }
}
