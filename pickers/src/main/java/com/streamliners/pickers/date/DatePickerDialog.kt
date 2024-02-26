package com.streamliners.pickers.date

import androidx.fragment.app.FragmentManager
import com.aminography.primecalendar.PrimeCalendar
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primedatepicker.picker.PrimeDatePicker
import com.aminography.primedatepicker.picker.builder.BaseRequestBuilder
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback
import com.streamliners.utils.DateTimeUtils
import com.streamliners.utils.DateTimeUtils.Format.YEAR_MONTH_DATE
import com.streamliners.utils.DateTimeUtils.reformatTime

object DatePickerDialog {

    class Params(
        val format: DateTimeUtils.Format,
        val prefill: String? = null,
        val minDate: String? = null,
        val maxDate: String? = null,
        val onPicked: (String) -> Unit
    )

    class MultipleDatesPickerParams(
        val format: DateTimeUtils.Format,
        val prefill: List<String> = emptyList(),
        val minDate: String? = null,
        val maxDate: String? = null,
        val onPicked: (List<String>) -> Unit
    )

    class RangePickerParams(
        val format: DateTimeUtils.Format,
        val prefill: Pair<String, String>?,
        val onPicked: (Pair<String, String>) -> Unit
    )

    fun show(
        fragmentManager: FragmentManager,
        params: Params
    ) {
        val prefill = getEffectivePrefillDate(params)
        val minDate = params.minDate?.let {
            PrimeCalendar.fromFormattedDate(it, params.format)
        }
        val maxDate = params.maxDate?.let {
            PrimeCalendar.fromFormattedDate(it, params.format)
        }

        var picker: BaseRequestBuilder<PrimeDatePicker, SingleDayPickCallback> = PrimeDatePicker
            .dialogWith(
                prefill ?: CivilCalendar()
            )
            .pickSingleDay { day ->
                params.onPicked(
                    day.toFormat(params.format)
                )
            }
            .run {
                prefill?.let {
                    initiallyPickedSingleDay(it)
                } ?: this
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

    private fun getEffectivePrefillDate(params: DatePickerDialog.Params): PrimeCalendar? {
        val min = params.minDate?.let {
            reformatTime(it, params.format, YEAR_MONTH_DATE)
        } ?: ""
        val max = params.maxDate?.let {
            reformatTime(it, params.format, YEAR_MONTH_DATE)
        } ?: "9"
        val prefill = params.prefill?.let {
            reformatTime(it, params.format, YEAR_MONTH_DATE)
        }
        val prefillCal = params.prefill?.let {
            PrimeCalendar.fromFormattedDate(it, params.format)
        }

        return prefill?.let {
            if (it in min..max) prefillCal else null
        }
    }

}