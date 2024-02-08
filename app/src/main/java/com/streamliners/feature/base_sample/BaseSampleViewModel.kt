package com.streamliners.feature.base_sample

import androidx.compose.runtime.mutableStateOf
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.ext.showingLoader
import com.streamliners.base.taskState.reLoad
import com.streamliners.base.taskState.taskStateOf
import com.streamliners.base.taskState.update
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.value
import com.streamliners.data.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseSampleViewModel @Inject constructor(
    private val factRepo: FactRepository
): BaseViewModel() {

    val numberInput1 = mutableStateOf(
        TextInputState("Number")
    )

    val numberInput2 = mutableStateOf(
        TextInputState("Number")
    )

    val numberInput3 = mutableStateOf(
        TextInputState("Number")
    )

    val fetchFactTaskState1 = taskStateOf<String>()

    val fetchFactTaskState2 = taskStateOf<String>()

    val fetchFactTaskState3 = taskStateOf<String>()

    fun fetchFactUsingLoadingDialog() {
        execute {
            fetchFactTaskState1.update(
                factRepo.getFact(numberInput1.value())
            )
        }
    }

    fun fetchFact() {
        execute(showLoadingDialog = false) {
            fetchFactTaskState2.reLoad {
                factRepo.getFact(numberInput2.value())
            }
        }
    }

    fun fetchFactUsingTaskExecutor() {
        execute(fetchFactTaskState3) {
            factRepo.getFact(numberInput3.value())
        }
    }

}