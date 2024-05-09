package com.streamliners.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateTimeUtils {

    sealed class Format(val pattern: String) {
        data object MONTH_DATE_DAY: Format("MM/dd E")
        data object DATE_MONTH_YEAR_1: Format("dd MMM yyyy")
        data object DATE_MONTH_YEAR_2: Format("dd/MM/yy")
        data object YEAR_MONTH_DATE: Format("yyyy-MM-dd")
        data object MONTH_YEAR: Format("MMM yyyy")
        data object HOUR_MIN_12: Format("hh:mm aa")
        data object HOUR_MIN_24: Format("HH:mm")
        data object TIME_DATE: Format("hh:mm aa, dd MMM, yyyy")
        data object DATE_DAY_TIME: Format("dd MMM yyyy, E, hh:mm aa")
        data object UTC: Format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        companion object
    }

    fun formatTime(
        format: Format,
        timestamp: Long = System.currentTimeMillis()
    ): String {
        return SimpleDateFormat(format.pattern, Locale.getDefault())
            .apply {
                if (format == Format.UTC) {
                    timeZone = TimeZone.getTimeZone("UTC")
                }
            }
            .format(Date(timestamp))
    }

    fun formatDateMonthAndYearIntsAs(date: Int, month: Int, year: Int, format: Format): String {
        val cal = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, date)
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
        }
        return formatTime(format, cal.timeInMillis)
    }

    fun parseAsDateMonthAndYearInts(time: String, format: Format): List<Int> {
        val cal = parseFormattedTime(format, time)
        return listOf(
            cal.get(Calendar.DAY_OF_MONTH),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.YEAR)
        )
    }

    fun parseFormattedTime(format: Format, time: String): Calendar {
        val date = SimpleDateFormat(format.pattern, Locale.getDefault())
            .parse(time) ?: error("Unable to parse date")
        val today = Calendar.getInstance();
        return today.apply {
            this.time = date
            if (get(Calendar.YEAR) == 1970) {
                set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH))
                set(Calendar.MONTH, today.get(Calendar.MONTH))
                set(Calendar.YEAR, today.get(Calendar.YEAR))
            }
        }
    }

    fun reformatTime(time: String, from: Format, to: Format): String {
        return formatTime(
            format = to,
            timestamp = parseFormattedTime(from, time).time.time
        )
    }

    fun parseTimeAsHourAndMinuteInts(format: Format, time: String): Pair<Int, Int> {
        val cal = parseFormattedTime(format, time)
        return cal.get(Calendar.HOUR_OF_DAY) to cal.get(Calendar.MINUTE)
    }

    fun formatHourAndMinuteInts(hour: Int, minute: Int, format: Format): String {
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        return formatTime(format, cal.time.time)
    }

    fun currentTimeHourAndMinuteInts(): Pair<Int, Int> {
        return with(Calendar.getInstance()) {
            get(Calendar.HOUR_OF_DAY) to get(Calendar.MINUTE)
        }
    }

    fun oneMonthFromNow(): Long {
        return Calendar.getInstance().run {
            add(Calendar.DAY_OF_MONTH, 30)
            time.time
        }
    }

    fun combineDateAndTime(
        date: String,
        dateFormat: Format,
        time: String,
        timeFormat: Format,
        outputFormat: Format
    ): String {
        return parseFormattedTime(dateFormat, date).run {
            val (hour, min) = parseTimeAsHourAndMinuteInts(timeFormat, time)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            formatTime(outputFormat, timeInMillis)
        }
    }

    object Timestamp {

        fun todayStart(): Long {
            return Calendar.getInstance().apply {
                resetTime()
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
        }

        fun yesterdayStart(): Long {
            return Calendar.getInstance().apply {
                timeInMillis = todayStart()
                add(Calendar.DAY_OF_MONTH, -1)
            }.timeInMillis
        }

        fun weekStart(): Long {
            return Calendar.getInstance().apply {
                resetTime()
                var day = get(Calendar.DAY_OF_WEEK)
                if (day == Calendar.SUNDAY) day = 8
                val diff = day - Calendar.MONDAY
                add(Calendar.DAY_OF_MONTH, -diff)
            }.timeInMillis
        }

        fun monthStart(): Long {
            return Calendar.getInstance().apply {
                resetTime()
                set(Calendar.DAY_OF_MONTH, 1)
            }.timeInMillis
        }

        fun lastMonthStart(): Long {
            return Calendar.getInstance().apply {
                resetTime()
                timeInMillis = monthStart()
                add(Calendar.MONTH, -1)
            }.timeInMillis
        }
    }

    fun daysFrom(ts: Long, days: Int): Long {
        return Calendar.getInstance().run {
            timeInMillis = ts
            add(Calendar.DAY_OF_MONTH, days)
            timeInMillis
        }
    }

    fun todayIsWithinDaysFrom(ts: Long, days: Int): Boolean {
        return System.currentTimeMillis() < daysFrom(ts, days)
    }

    fun Calendar.resetTime() {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    fun parseUTCTime(time: String): Long {
        val dateFormat = SimpleDateFormat(Format.UTC.pattern, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.parse(time)?.time ?: error("Unable to parse")
    }

}
