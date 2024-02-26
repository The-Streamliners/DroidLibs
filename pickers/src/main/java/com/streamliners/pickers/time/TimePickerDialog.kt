package com.streamliners.pickers.time

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.streamliners.utils.DateTimeUtils

object TimePickerDialog {

    class Params(
        val title: String,
        val format: DateTimeUtils.Format,
        val prefill: String? = null,
        val minTime: String? = null,
        val maxTime: String? = null,
        val onPicked: (String) -> Unit
    )

    fun show(
        context: Context,
        fragmentManager: FragmentManager,
        params: Params
    ) {
        val (hour, minute) =
            params.prefill?.let { time ->
                DateTimeUtils.parseTimeAsHourAndMinuteInts(params.format, time)
            } ?: DateTimeUtils.currentTimeHourAndMinuteInts()

        val picker = MaterialTimePicker.Builder()
            .setTitleText(params.title)
            .setHour(hour)
            .setMinute(minute)
            .build()

        picker.addOnPositiveButtonClickListener {
            val formattedTime =
                DateTimeUtils.formatHourAndMinuteInts(picker.hour, picker.minute, params.format)
            if (isTimeInRange(formattedTime, params.minTime, params.maxTime, params.format)) {
                params.onPicked(formattedTime)
            } else {
                val minTime = params.minTime ?: "-"
                val maxTime = params.maxTime ?: "-"
                Toast.makeText(
                    context,
                    "Time must be between ($minTime and $maxTime)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        picker.show(fragmentManager, null)
    }

}

private fun isTimeInRange(
    selectedTime: String,
    minTime: String?,
    maxTime: String?,
    format: DateTimeUtils.Format
): Boolean {
    val selected =
        DateTimeUtils.reformatTime(selectedTime, format, DateTimeUtils.Format.HOUR_MIN_24)
    val min = minTime?.let {
        DateTimeUtils.reformatTime(it, format, DateTimeUtils.Format.HOUR_MIN_24)
    } ?: "00:00"
    val max = maxTime?.let {
        DateTimeUtils.reformatTime(it, format, DateTimeUtils.Format.HOUR_MIN_24)
    } ?: "23:59"
    return selected in min..max
}