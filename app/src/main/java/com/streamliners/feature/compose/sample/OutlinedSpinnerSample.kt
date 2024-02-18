package com.streamliners.feature.compose.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.base.BaseActivity
import com.streamliners.base.ext.showMessageDialog
import com.streamliners.compose.comp.spinner.OutlinedSpinner
import com.streamliners.compose.comp.spinner.state.SpinnerState
import com.streamliners.compose.comp.spinner.state.value
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.isValid
import com.streamliners.compose.comp.textInput.state.validate
import com.streamliners.compose.comp.textInput.state.value

@Composable
fun BaseActivity.OutlinedSpinnerSample() {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Outlined Spinner",
                style = MaterialTheme.typography.titleLarge
            )

            StringOutlinedSpinnerSample()

            CustomOutlinedSpinnerSample()
        }
    }
}

context(BaseActivity, ColumnScope)
@Composable
fun StringOutlinedSpinnerSample() {
    val state = remember {
        mutableStateOf(
            TextInputState("Country")
        )
    }

    OutlinedSpinner(
        options = listOf("Bharat", "USA", "Russia", "China"),
        state = state
    )

    Button(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        onClick = {
            state.validate()
            if (state.isValid()) {
                showMessageDialog(
                    title = "Confirm selection",
                    message = "You selected : ${state.value()}"
                )
            }
        }
    ) {
        Text(text = "Submit")
    }
}

context(BaseActivity, ColumnScope)
@Composable
fun CustomOutlinedSpinnerSample() {

    data class Person(
        val id: Int,
        val name: String
    )

    val state = remember {
        SpinnerState<Person>(
            state = mutableStateOf(null),
            textInputState = mutableStateOf(TextInputState("Person")),
            labelExtractor = { it.name }
        )
    }

    OutlinedSpinner(
        options = listOf(
            Person(1, "Swami Vivekananda"),
            Person(2, "Dr. Homi J. Bhabha"),
            Person(3, "Dr. Vikram Sarabhai"),
            Person(4, "Dr. APJ Abdul Kalam")
        ),
        state = state,
        allowInput = true
    )

    Button(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        onClick = {
            state.ifSelected { person ->
                showMessageDialog(
                    title = "Confirm selection",
                    message = "You selected : $person"
                )
            }
        }
    ) {
        Text(text = "Submit")
    }
}
