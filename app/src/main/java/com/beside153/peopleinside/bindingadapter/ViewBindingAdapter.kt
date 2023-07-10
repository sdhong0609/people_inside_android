package com.beside153.peopleinside.bindingadapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.beside153.peopleinside.R

private const val MAX_CHOICE_COUNT = 5

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("isVisibleInVisible")
fun View.isVisibleInVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
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

@BindingAdapter("setGenderTextViewPlace")
fun View.setGenderTextViewPlace(showAlert: Boolean) {
    val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    layoutParams.topToBottom = if (showAlert) R.id.duplicateNicknameTextView else R.id.nicknameLayout
    this.layoutParams = layoutParams
}

@BindingAdapter("setGenderTextViewPlace2")
fun View.setGenderTextViewPlace2(showAlert: Boolean) {
    val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    layoutParams.topToBottom = if (showAlert) R.id.plsInputTextView else R.id.editNicknameLayout
    this.layoutParams = layoutParams
}

@BindingAdapter("setGenderTextViewPlace3")
fun View.setGenderTextViewPlace3(showAlert: Boolean) {
    val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    layoutParams.topToBottom = if (showAlert) R.id.isDuplicateTextView else R.id.editNicknameLayout
    this.layoutParams = layoutParams
}

@BindingAdapter("contentChoiceButtonText")
fun Button.contentChoiceButtonText(choiceCount: Int) {
    text = if (choiceCount >= MAX_CHOICE_COUNT) {
        resources.getString(R.string.content_choice_button_complete, choiceCount)
    } else {
        resources.getString(R.string.content_choice_button, choiceCount)
    }
}
