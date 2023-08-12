package com.beside153.peopleinside.bindingadapter

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.beside153.peopleinside.R

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("isVisibleInVisible")
fun View.isVisibleInVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("setGenderTextViewPlace")
fun View.setGenderTextViewPlace(showAlert: Boolean) {
    val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    layoutParams.topToBottom = if (showAlert) R.id.signupDuplicateTextView else R.id.nicknameLayout
    this.layoutParams = layoutParams
}

@BindingAdapter("setGenderTextViewPlace2")
fun View.setGenderTextViewPlace2(showAlert: Boolean) {
    val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    layoutParams.topToBottom = if (showAlert) R.id.editProfilePlsInputTextView else R.id.editNicknameLayout
    this.layoutParams = layoutParams
}

@BindingAdapter("setGenderTextViewPlace3")
fun View.setGenderTextViewPlace3(showAlert: Boolean) {
    val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    layoutParams.topToBottom = if (showAlert) R.id.editProfileDuplicateTextView else R.id.editNicknameLayout
    this.layoutParams = layoutParams
}

@BindingAdapter("setGenderTextViewPlace4")
fun View.setGenderTextViewPlace4(showAlert: Boolean) {
    val layoutParams = this.layoutParams as ConstraintLayout.LayoutParams
    layoutParams.topToBottom = if (showAlert) R.id.editProfileBadWordTextView else R.id.editNicknameLayout
    this.layoutParams = layoutParams
}
