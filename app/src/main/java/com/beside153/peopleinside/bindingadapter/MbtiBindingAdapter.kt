package com.beside153.peopleinside.bindingadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.beside153.peopleinside.R

@BindingAdapter("mbtiLargeImg")
fun ImageView.mbtiLargeImg(mbti: String) {
    val imgRes = when (mbti.uppercase()) {
        "ENFJ" -> R.drawable.mbti_large_img_enfj
        "ENFP" -> R.drawable.mbti_large_img_enfp
        "ENTJ" -> R.drawable.mbti_large_img_entj
        "ENTP" -> R.drawable.mbti_large_img_entp
        "ESFJ" -> R.drawable.mbti_large_img_esfj
        "ESFP" -> R.drawable.mbti_large_img_esfp
        "ESTJ" -> R.drawable.mbti_large_img_estj
        "ESTP" -> R.drawable.mbti_large_img_estp

        "INFJ" -> R.drawable.mbti_large_img_infj
        "INFP" -> R.drawable.mbti_large_img_infp
        "INTJ" -> R.drawable.mbti_large_img_intj
        "INTP" -> R.drawable.mbti_large_img_intp
        "ISFJ" -> R.drawable.mbti_large_img_isfj
        "ISFP" -> R.drawable.mbti_large_img_isfp
        "ISTJ" -> R.drawable.mbti_large_img_istj
        "ISTP" -> R.drawable.mbti_large_img_istp

        else -> R.color.white
    }

    setImageResource(imgRes)
}

@BindingAdapter("mbtiCircleImg")
fun ImageView.mbtiCircleImg(mbti: String) {
    val imgRes = when (mbti.uppercase()) {
        "ENFJ" -> R.drawable.mbti_circle_icon_enfj
        "ENFP" -> R.drawable.mbti_circle_icon_enfp
        "ENTJ" -> R.drawable.mbti_circle_icon_entj
        "ENTP" -> R.drawable.mbti_circle_icon_entp
        "ESFJ" -> R.drawable.mbti_circle_icon_esfj
        "ESFP" -> R.drawable.mbti_circle_icon_esfp
        "ESTJ" -> R.drawable.mbti_circle_icon_estj
        "ESTP" -> R.drawable.mbti_circle_icon_estp

        "INFJ" -> R.drawable.mbti_circle_icon_infj
        "INFP" -> R.drawable.mbti_circle_icon_infp
        "INTJ" -> R.drawable.mbti_circle_icon_intj
        "INTP" -> R.drawable.mbti_circle_icon_intp
        "ISFJ" -> R.drawable.mbti_circle_icon_isfj
        "ISFP" -> R.drawable.mbti_circle_icon_isfp
        "ISTJ" -> R.drawable.mbti_circle_icon_istj
        "ISTP" -> R.drawable.mbti_circle_icon_istp

        else -> R.drawable.mbti_circle_icon_enfj
    }

    setImageResource(imgRes)
}

@BindingAdapter("mbtiHeartImg")
fun ImageView.mbtiHeartImg(mbti: String) {
    val imgRes = when (mbti.uppercase()) {
        "ENFJ" -> R.drawable.mbti_heart_icon_enfj
        "ENFP" -> R.drawable.mbti_heart_icon_enfp
        "ENTJ" -> R.drawable.mbti_heart_icon_entj
        "ENTP" -> R.drawable.mbti_heart_icon_entp
        "ESFJ" -> R.drawable.mbti_heart_icon_esfj
        "ESFP" -> R.drawable.mbti_heart_icon_esfp
        "ESTJ" -> R.drawable.mbti_heart_icon_estj
        "ESTP" -> R.drawable.mbti_heart_icon_estp

        "INFJ" -> R.drawable.mbti_heart_icon_infj
        "INFP" -> R.drawable.mbti_heart_icon_infp
        "INTJ" -> R.drawable.mbti_heart_icon_intj
        "INTP" -> R.drawable.mbti_heart_icon_intp
        "ISFJ" -> R.drawable.mbti_heart_icon_isfj
        "ISFP" -> R.drawable.mbti_heart_icon_isfp
        "ISTJ" -> R.drawable.mbti_heart_icon_istj
        "ISTP" -> R.drawable.mbti_heart_icon_istp

        else -> R.drawable.mbti_heart_icon_enfj
    }

    setImageResource(imgRes)
}

@BindingAdapter("mbtiBackground")
fun TextView.mbtiBackground(mbti: String) {
    val background = when (mbti.uppercase()) {
        "ENFJ" -> R.drawable.ENFJ
        "ENFP" -> R.drawable.ENFP
        "ENTJ" -> R.drawable.ENTJ
        "ENTP" -> R.drawable.ENTP
        "ESFJ" -> R.drawable.ESFJ
        "ESFP" -> R.drawable.ESFP
        "ESTJ" -> R.drawable.ESTJ
        "ESTP" -> R.drawable.ESTP

        "INFJ" -> R.drawable.INFJ
        "INFP" -> R.drawable.INFP
        "INTJ" -> R.drawable.INTJ
        "INTP" -> R.drawable.INTP
        "ISFJ" -> R.drawable.ISFJ
        "ISFP" -> R.drawable.ISFP
        "ISTJ" -> R.drawable.ISTJ
        "ISTP" -> R.drawable.ISTP

        else -> R.drawable.ENFJ
    }

    setBackgroundResource(background)
}

@BindingAdapter("mbtiTextColor")
fun TextView.mbtiTextColor(mbti: String) {
    val textColor = when (mbti.uppercase()) {
        "ENFJ", "ENFP", "ENTJ", "ESTJ", "ESTP", "INFP", "ISFJ", "ISFP", "ISTJ" -> ContextCompat.getColor(
            context,
            R.color.black
        )

        "ENTP", "ESFJ", "ESFP", "INFJ", "INTJ", "INTP", "ISTP" -> ContextCompat.getColor(context, R.color.white)

        else -> ContextCompat.getColor(context, R.color.black)
    }

    setTextColor(textColor)
}

@BindingAdapter("mbtiLeftIcon")
fun TextView.mbtiLeftIcon(mbti: String) {
    val leftIcon = when (mbti.uppercase()) {
        "ENFJ", "ENFP", "ENTJ", "ESTJ", "ESTP", "INFP", "ISFJ", "ISFP", "ISTJ" -> R.drawable.ic_clap_hands_black

        "ENTP", "ESFJ", "ESFP", "INFJ", "INTJ", "INTP", "ISTP" -> R.drawable.ic_clap_hands_white

        else -> R.drawable.ic_clap_hands_black
    }

    setCompoundDrawablesRelativeWithIntrinsicBounds(leftIcon, 0, 0, 0)
}
