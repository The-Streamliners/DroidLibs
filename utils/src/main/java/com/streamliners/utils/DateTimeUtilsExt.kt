package com.streamliners.utils

fun DateTimeUtils.Format.Companion.dateOnly(): List<DateTimeUtils.Format> {
    return listOf(
        DateTimeUtils.Format.MONTH_DATE_DAY,
        DateTimeUtils.Format.DATE_MONTH_YEAR_1,
        DateTimeUtils.Format.DATE_MONTH_YEAR_2,
        DateTimeUtils.Format.YEAR_MONTH_DATE
    )
}

fun DateTimeUtils.Format.Companion.timeOnly(): List<DateTimeUtils.Format> {
    return listOf(
        DateTimeUtils.Format.HOUR_MIN_12,
        DateTimeUtils.Format.HOUR_MIN_24
    )
}