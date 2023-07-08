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
