package com.streamliners.pickers.date

import androidx.fragment.app.FragmentManager
import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primedatepicker.picker.PrimeDatePicker
import com.aminography.primedatepicker.picker.builder.RangeDaysRequestBuilder

fun DatePickerDialog.showRangePicker(
    fragmentManager: FragmentManager,
    params: DatePickerDialog.RangePickerParams
) {
    val prefillRange = params.prefill?.run {

        val start = PrimeCalendar.fromFormattedDate(first, params.format)
        val end = PrimeCalendar.fromFormattedDate(second, params.format)

        start to end
    }

    val minDate = params.minDate?.let {
        PrimeCalendar.fromFormattedDate(it, params.format)
    }
    val maxDate = params.maxDate?.let {
        PrimeCalendar.fromFormattedDate(it, params.format)
    }

    var picker = PrimeDatePicker
        .dialogWith(
            prefillRange?.first ?: CivilCalendar()
        )
        .pickRangeDays { start, end ->
            params.onPicked(
                start.toFormat(params.format) to end.toFormat(params.format)
            )
        }.run {
            prefillRange?.let { (start, end) ->
                initiallyPickedRangeDays(start, end)
            } ?: this
        }

    minDate?.let {
        picker = picker.minPossibleDate(it) as RangeDaysRequestBuilder<PrimeDatePicker>
    }
    maxDate?.let {
        picker = picker.maxPossibleDate(it) as RangeDaysRequestBuilder<PrimeDatePicker>
    }

    picker
        .build()
        .show(fragmentManager, "DP")
}