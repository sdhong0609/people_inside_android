package com.beside153.peopleinside.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun intervalBetweenDateText(beforeDate: String): String {
    val nowFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(getCurrentTime())
    val beforeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(beforeDate)

    val diffMilliseconds = (nowFormat?.time ?: 0) - (beforeFormat?.time ?: 0)
    val diffSeconds = diffMilliseconds / 1000
    val diffMinutes = diffMilliseconds / (60 * 1000)
    val diffHours = diffMilliseconds / (60 * 60 * 1000)
    val diffDays = diffMilliseconds / (24 * 60 * 60 * 1000)

    val nowCalendar = Calendar.getInstance().apply {
        if (nowFormat != null) {
            time = nowFormat
        }
    }
    val beforeCalendar = Calendar.getInstance().apply {
        if (beforeFormat != null) {
            time = beforeFormat
        }
    }

    val diffYears = nowCalendar.get(Calendar.YEAR) - beforeCalendar.get(Calendar.YEAR)
    var diffMonths = diffYears * 12 + nowCalendar.get(Calendar.MONTH) - beforeCalendar.get(Calendar.MONTH)
    if (nowCalendar.get(Calendar.DAY_OF_MONTH) < beforeCalendar.get(Calendar.DAY_OF_MONTH)) {
        // 현재 날짜의 일(day) 값이 이전 날짜의 일(day) 값보다 작은 경우에만 월 차이를 1 감소시킴
        // Ex) 5.31일과 6.2일은 2일차이지만 month가 1로 계속 나오는 이슈해결을 위해서
        diffMonths--
    }

    if (diffYears > 0) {
        return "${diffYears}년 전"
    }
    if (diffMonths > 0) {
        return "${diffMonths}개월 전"
    }
    if (diffDays > 0) {
        return "${diffDays}일 전"
    }
    if (diffHours > 0) {
        return "${diffHours}시간 전"
    }
    if (diffMinutes > 0) {
        return "${diffMinutes}분 전"
    }
    if (diffSeconds > 0) {
        return "${diffSeconds}초 전"
    }
    if (diffSeconds > -1) {
        return "방금"
    }
    return ""
}

private fun getCurrentTime(): String {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(Date())
}
