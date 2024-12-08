package com.manyacov.data.rate_tracker.util

import java.util.Calendar
import java.util.Date

internal fun isLessThanOneMonthAgo(date: Date?): Boolean {
    val currentDate = Date()
    val calendar = Calendar.getInstance().apply {
        time = currentDate
        add(Calendar.MONTH, -1)
    }
    return date?.after(calendar.time) ?: false
}