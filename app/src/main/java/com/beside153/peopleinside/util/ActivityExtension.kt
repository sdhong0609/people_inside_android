package com.beside153.peopleinside.util

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.beside153.peopleinside.R

fun Activity.setOpenActivityAnimation() {
    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
}

fun Activity.setCloseActivityAnimation() {
    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
}

fun AppCompatActivity.addBackPressedAnimation(additionalFunction: (() -> Unit)? = null) {
    onBackPressedDispatcher.addCallback(
        this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (additionalFunction != null) additionalFunction()
                finish()
                setCloseActivityAnimation()
            }
        }
    )
}
