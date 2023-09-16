package com.streamliners.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.streamliners.base.BaseActivity
import com.streamliners.base.uiEvent.UiEventDialogs
import com.streamliners.ui.main.DroidLibsApp
import com.streamliners.ui.theme.DroidLibsTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroidLibsTheme {
                DroidLibsApp()
                UiEventDialogs()
            }
        }
    }
}