package com.beside153.peopleinside.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
fun ImageView.mbtiImg(imgId: Int) {
    Glide.with(this).load(imgId).error(android.R.color.transparent).into(this)
}

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("yearTextColor")
fun TextView.yearTextColor(isChosen: Boolean) {
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
