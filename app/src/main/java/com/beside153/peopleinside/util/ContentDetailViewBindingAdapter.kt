package com.beside153.peopleinside.util

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.beside153.peopleinside.R

@BindingAdapter("contentKeywordBackground")
fun TextView.contentKeywordBackground(mediaType: String) {
    val background = if (mediaType == resources.getString(R.string.movie_english)) {
        ContextCompat.getDrawable(context, R.drawable.background_white_rounded)
    } else {
        ContextCompat.getDrawable(context, R.drawable.background_black_rounded)
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
