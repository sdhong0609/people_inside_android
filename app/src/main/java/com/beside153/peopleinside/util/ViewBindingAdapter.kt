package com.beside153.peopleinside.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("posterUrl")
fun ImageView.posterUrl(url: String) {
    Glide.with(this).load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2$url").error(android.R.color.transparent)
        .into(this)
}

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
