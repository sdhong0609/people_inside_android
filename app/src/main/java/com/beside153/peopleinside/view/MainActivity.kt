package com.beside153.peopleinside.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.databinding.ActivityMainBinding
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.view.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.bottomNavFragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.apply {
            setOnItemSelectedListener { menuItem ->
                if (App.prefs.getNickname() == getString(R.string.nonmember_nickname)) {
                    when (menuItem.itemId) {
                        R.id.myPageFragment -> {
                            startActivity(LoginActivity.newIntent(this@MainActivity))
                            setOpenActivityAnimation()
                        }
                    }
                    return@setOnItemSelectedListener true
                }

                menuItem.onNavDestinationSelected(navController)
            }
        }

        val isFirstEnter = intent.getBooleanExtra(FIRST_ENTER, false)
        if (isFirstEnter) showToast(R.string.welcome)
    }

    companion object {
        private const val FIRST_ENTER = "FIRST_ENTER"

        fun newIntent(context: Context, isFirstEnter: Boolean): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(FIRST_ENTER, isFirstEnter)
            return intent
        }
    }
}
