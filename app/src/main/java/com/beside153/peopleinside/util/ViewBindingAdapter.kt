package com.beside153.peopleinside.util

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.beside153.peopleinside.R
import com.bumptech.glide.Glide

private const val MAX_CHOICE_COUNT = 5

@BindingAdapter("posterUrl")
fun ImageView.posterUrl(url: String) {
    Glide.with(this).load("https://www.themoviedb.org/t/p/w600_and_h900_bestv2$url").error(R.drawable.background_poster)
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

@BindingAdapter("setGenderTextViewPlace")
fun View.setGenderTextViewPlace(isDuplicate: Boolean) {
    val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    layoutParams.topToBottom = if (isDuplicate) R.id.duplicateNicknameTextView else R.id.nicknameLayout
    this.layoutParams = layoutParams
}

@BindingAdapter("contentChoiceButtonText")
fun Button.contentChoiceButtonText(choiceCount: Int) {
    text = if (choiceCount >= MAX_CHOICE_COUNT) {
        resources.getString(R.string.content_choice_button_complete)
    } else {
        resources.getString(R.string.content_choice_button, choiceCount)
    }
}

@BindingAdapter("bookmarkImg")
fun ImageView.bookmarkImg(bookmarked: Boolean) {
    if (bookmarked) {
        setImageResource(R.drawable.ic_bookmark_filled_gray)
    } else {
        setImageResource(
            R.drawable.ic_bookmark_empty_gray
        )
    }
}
