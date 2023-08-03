package com.beside153.peopleinside.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.beside153.peopleinside.App
import com.beside153.peopleinside.R
import com.beside153.peopleinside.base.BaseActivity
import com.beside153.peopleinside.databinding.ActivityMainBinding
import com.beside153.peopleinside.util.setOpenActivityAnimation
import com.beside153.peopleinside.util.showToast
import com.beside153.peopleinside.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private var previousMenuItemId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.bottomNavFragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.apply {
            setOnItemSelectedListener { menuItem ->
                if (!App.prefs.getIsMember() && menuItem.itemId == R.id.myPageFragment) {
                    previousMenuItemId = selectedItemId

                    loginActivityLauncher.launch(LoginActivity.newIntent(this@MainActivity))
                    setOpenActivityAnimation()
                    return@setOnItemSelectedListener true
                }
                menuItem.onNavDestinationSelected(navController)
            }
        }

        val isFirstEnter = intent.getBooleanExtra(FIRST_ENTER, false)
        if (isFirstEnter) showToast(R.string.welcome)
    }

    private val loginActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == BACK_FROM_LOGINACTIVITY) {
                if (previousMenuItemId != 0) {
                    binding.bottomNavigationView.menu.findItem(previousMenuItemId)?.isChecked = true
                    previousMenuItemId = 0
                }
            }
        }

    companion object {
        private const val FIRST_ENTER = "FIRST_ENTER"
        private const val BACK_FROM_LOGINACTIVITY = 111

        fun newIntent(context: Context, isFirstEnter: Boolean): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(FIRST_ENTER, isFirstEnter)
            return intent
        }
    }
}
