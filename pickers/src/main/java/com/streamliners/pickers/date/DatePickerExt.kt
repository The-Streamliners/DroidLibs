package com.streamliners.pickers.date

import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.common.CalendarFactory
import com.aminography.primecalendar.common.CalendarType
import com.streamliners.utils.DateTimeUtils

fun PrimeCalendar.toFormat(format: DateTimeUtils.Format): String {
    return DateTimeUtils.formatDateMonthAndYearIntsAs(
        dayOfMonth, month, year, format
    )
}

fun PrimeCalendar.Companion.fromFormattedDate(
    date: String,
    format: DateTimeUtils.Format
): PrimeCalendar {
    val (day, month, year) = DateTimeUtils.parseAsDateMonthAndYearInts(date, format)
    return CalendarFactory.newInstance(CalendarType.CIVIL).apply {
        set(year, month, day)
    }
}

