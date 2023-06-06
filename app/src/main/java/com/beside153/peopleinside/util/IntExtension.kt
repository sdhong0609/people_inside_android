package com.beside153.peopleinside.util

import android.util.DisplayMetrics

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()
