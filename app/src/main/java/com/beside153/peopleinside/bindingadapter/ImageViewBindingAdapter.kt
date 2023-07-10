package com.beside153.peopleinside.bindingadapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.beside153.peopleinside.R
import com.bumptech.glide.Glide

@BindingAdapter("posterUrl")
fun ImageView.posterUrl(url: String) {
    Glide.with(this).load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2$url")
        .error(R.drawable.background_white_rounded)
        .into(this)
}

@BindingAdapter("setImgRes")
fun ImageView.setImgRes(@DrawableRes imgRes: Int) {
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

@BindingAdapter("radioButtonImg")
fun ImageView.radioButtonImg(isChecked: Boolean) {
    if (isChecked) {
        this.setImageResource(R.drawable.ic_radio_button_checked)
    } else {
        this.setImageResource(R.drawable.ic_radio_button_unchecked)
    }
}

@BindingAdapter("blackHeartImg")
fun ImageView.blackHeartImg(liked: Boolean) {
    if (liked) {
        this.setImageResource(R.drawable.ic_heart_filled)
    } else {
        this.setImageResource(R.drawable.ic_heart_empty)
    }
}
