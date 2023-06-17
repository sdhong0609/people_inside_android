package com.beside153.peopleinside.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.beside153.peopleinside.R
import com.bumptech.glide.Glide

@BindingAdapter("posterUrl")
fun ImageView.posterUrl(url: String) {
    Glide.with(this).load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2$url").error(android.R.color.transparent)
        .into(this)
}

@BindingAdapter("mbtiImg")
fun ImageView.mbtiImg(@DrawableRes imgRes: Int) {
    this.setImageResource(imgRes)
}

@BindingAdapter("checkBoxImg")
fun ImageView.checkBoxImg(didCheck: Boolean) {
    if (didCheck) {
        this.setImageResource(R.drawable.ic_checkbox_checked)
    } else {
        this.setImageResource(R.drawable.ic_checkbox_unchecked)
    }
}

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("userInfoTextColor")
fun TextView.userInfoTextColor(isChosen: Boolean) {
    val textColor = if (isChosen) {
        ContextCompat.getColor(context, R.color.black)
    } else {
        ContextCompat.getColor(
            context,
            R.color.gray_500
        )
    }
    this.setTextColor(textColor)
}

@BindingAdapter("setGenderTextViewTopToBottom")
fun View.setGenderTextViewTopToBottom(isDuplicate: Boolean) {
    val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    layoutParams.topToBottom = if (isDuplicate) R.id.duplicateNicknameTextView else R.id.nicknameLayout
    this.layoutParams = layoutParams
}
