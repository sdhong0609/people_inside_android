package com.beside153.peopleinside.bindingadapter

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.beside153.peopleinside.R

private const val MAX_CHOICE_COUNT = 5

@BindingAdapter("contentChoiceButtonText")
fun Button.contentChoiceButtonText(choiceCount: Int) {
    text = if (choiceCount >= MAX_CHOICE_COUNT) {
        resources.getString(R.string.content_choice_button_complete, choiceCount)
    } else {
        resources.getString(R.string.content_choice_button, choiceCount)
    }
}
