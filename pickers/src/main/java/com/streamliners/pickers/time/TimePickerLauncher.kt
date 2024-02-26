package com.streamliners.pickers.time

import com.streamliners.base.BaseActivity

typealias ShowTimePicker = (TimePickerDialog.Params) -> Unit

fun BaseActivity.showTimePicker(params: TimePickerDialog.Params) {
    TimePickerDialog.show(this, supportFragmentManager, params)
}