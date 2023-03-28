package com.mendelin.usersmanagement.domain

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private const val DATE_FORMAT_STRING_WITH_MILLISECONDS = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    private const val DATE_FORMAT_TIME = "HH:mm"

    fun String.toTimeFormat(): String {
        val date: Date = SimpleDateFormat(DATE_FORMAT_STRING_WITH_MILLISECONDS, Locale.getDefault())
            .parse(this) ?: Date()
        return SimpleDateFormat(DATE_FORMAT_TIME, Locale.getDefault()).format(date)
    }
}