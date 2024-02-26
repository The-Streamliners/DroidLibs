package com.streamliners.pickers.date

import androidx.fragment.app.FragmentManager
import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primedatepicker.picker.PrimeDatePicker
import com.streamliners.utils.DateTimeUtils

fun DatePickerDialog.showRangePicker(
    fragmentManager: FragmentManager,
    params: DatePickerDialog.RangePickerParams
) {
    val prefillRange = params.prefill?.run {

        val start = PrimeCalendar.fromFormattedDate(first, params.format)
        val end = PrimeCalendar.fromFormattedDate(second, params.format)

        start to end
    }

    var picker = PrimeDatePicker
        .dialogWith(
            prefillRange?.first ?: CivilCalendar()
        )
        .pickRangeDays { start, end ->
            params.onPicked(
                start.toFormat(params.format) to end.toFormat(params.format)
            )
        }

    prefillRange?.let { (start, end) ->
        picker = picker.initiallyPickedRangeDays(start, end)
    }

    picker
        .build()
        .show(fragmentManager, "DP")
}