package com.streamliners.pickers.date

import com.streamliners.base.BaseActivity
import com.streamliners.pickers.date.DatePickerDialog.Params
import com.streamliners.pickers.date.DatePickerDialog.RangePickerParams

typealias ShowDatePicker = (params: Params) -> Unit

typealias ShowDateRangePicker = (params: RangePickerParams) -> Unit

fun BaseActivity.showDatePickerDialog(params: Params) {
    DatePickerDialog.show(
        supportFragmentManager, params
    )
}

fun BaseActivity.showDateRangePickerDialog(params: RangePickerParams) {
    DatePickerDialog.showRangePicker(
        supportFragmentManager, params
    )
}