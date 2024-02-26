package com.streamliners.utils

fun DateTimeUtils.Format.Companion.dateOnly(): List<DateTimeUtils.Format> {
    return listOf(
        DateTimeUtils.Format.MONTH_DATE_DAY,
        DateTimeUtils.Format.DATE_MONTH_YEAR_1,
        DateTimeUtils.Format.DATE_MONTH_YEAR_2,
        DateTimeUtils.Format.YEAR_MONTH_DATE
    )
}