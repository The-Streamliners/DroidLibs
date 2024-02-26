package com.streamliners.pickers.date

import androidx.fragment.app.FragmentManager
import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primedatepicker.picker.PrimeDatePicker
import com.aminography.primedatepicker.picker.builder.BaseRequestBuilder
import com.aminography.primedatepicker.picker.callback.MultipleDaysPickCallback
import com.streamliners.utils.DateTimeUtils.Format.YEAR_MONTH_DATE
import com.streamliners.utils.DateTimeUtils.reformatTime

fun DatePickerDialog.showMultipleDatesPicker(
    fragmentManager: FragmentManager,
    params: DatePickerDialog.MultipleDatesPickerParams
) {
    val prefill = getEffectivePrefillDates(params)
    val minDate = params.minDate?.let {
        PrimeCalendar.fromFormattedDate(it, params.format)
    }
    val maxDate = params.maxDate?.let {
        PrimeCalendar.fromFormattedDate(it, params.format)
    }

    var picker: BaseRequestBuilder<PrimeDatePicker, MultipleDaysPickCallback> = PrimeDatePicker
        .dialogWith(
            CivilCalendar()
        )
        .pickMultipleDays { days ->
            params.onPicked(
                days.map { it.toFormat(params.format) }
            )
        }
        .run {
            initiallyPickedMultipleDays(prefill)
        }

    minDate?.let {
        picker = picker.minPossibleDate(it)
    }
    maxDate?.let {
        picker = picker.maxPossibleDate(it)
    }

    picker
        .build()
        .show(fragmentManager, "DP")
}

private fun getEffectivePrefillDates(params: DatePickerDialog.MultipleDatesPickerParams): List<PrimeCalendar> {
    val min = params.minDate?.let {
        reformatTime(it, params.format, YEAR_MONTH_DATE)
    } ?: ""
    val max = params.maxDate?.let {
        reformatTime(it, params.format, YEAR_MONTH_DATE)
    } ?: "9"

    return params.prefill.filter {
        val reformattedDate = reformatTime(it, params.format, YEAR_MONTH_DATE)
        reformattedDate in min..max
    }.map {
        PrimeCalendar.fromFormattedDate(it, params.format)
    }
}