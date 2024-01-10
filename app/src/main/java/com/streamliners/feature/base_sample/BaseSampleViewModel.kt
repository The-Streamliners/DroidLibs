package com.streamliners.feature.base_sample

import androidx.compose.runtime.mutableStateOf
import com.streamliners.base.BaseViewModel
import com.streamliners.base.exception.failure
import com.streamliners.base.ext.execute
import com.streamliners.base.ext.showingLoader
import com.streamliners.base.uiState.taskStateOf
import com.streamliners.base.uiState.update
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.value
import com.streamliners.data.FactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseSampleViewModel @Inject constructor(
    private val factRepo: FactRepository
): BaseViewModel() {

    val number = mutableStateOf(
        TextInputState("Number")
    )

    val fact = taskStateOf<String>()

    fun fetchFactUsingLoadingDialog() {
        execute(showLoadingDialog = false) {

            val num = number.value().toIntOrNull()
                ?: failure("Invalid input, please enter a number!")

            showingLoader {
                fact.update(
                    factRepo.getFact(num)
                )
            }
        }
    }

}