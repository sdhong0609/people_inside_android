package com.beside153.peopleinside.util

import android.util.DisplayMetrics
import kotlin.math.roundToInt

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()

fun Float.dpToPx(displayMetrics: DisplayMetrics): Float = this * displayMetrics.density

fun Float.roundToHalf(): Float = ((this * 2).roundToInt() / 2.0).toFloat()
