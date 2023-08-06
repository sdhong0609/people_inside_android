package com.beside153.peopleinside.bindingadapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.beside153.peopleinside.R

@BindingAdapter("recommendTabTextColor")
fun TextView.recommendTabTextColor(isChosen: Boolean) {
    val textColor = if (isChosen) {
        ContextCompat.getColor(context, R.color.white)
    } else {
        ContextCompat.getColor(
            context,
            R.color.gray_500
        )
    }
    setTextColor(textColor)
}

@BindingAdapter("recommendTabBackground")
fun TextView.recommendTabBackground(isChosen: Boolean) {
    val background = if (isChosen) {
        ContextCompat.getDrawable(context, R.drawable.bg_recommend_tab_layout)
    } else {
        null
    }
    setBackground(background)
}

@BindingAdapter("contentKeywordBackground")
fun TextView.contentKeywordBackground(mediaType: String) {
    val background = if (mediaType == resources.getString(R.string.movie_english)) {
        ContextCompat.getDrawable(context, R.drawable.bg_white_rounded)
    } else {
        ContextCompat.getDrawable(context, R.drawable.bg_black_radius4dp)
    }
    this.background = background
}

@BindingAdapter("contentKeywordTextColor")
fun TextView.contentKeywordTextColor(mediaType: String) {
    val textColor = if (mediaType == resources.getString(R.string.movie_english)) {
        ContextCompat.getColor(context, R.color.black)
    } else {
        ContextCompat.getColor(context, R.color.white)
    }
    setTextColor(textColor)
}

@BindingAdapter("mediaType", "movieKeyword", "tvKeyword")
fun TextView.contentKeywordText(mediaType: String, movieKeyword: String, tvKeyword: String) {
    text = if (mediaType == resources.getString(R.string.movie_english)) {
        movieKeyword
    } else {
        tvKeyword
    }
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
    setTextColor(textColor)
}
