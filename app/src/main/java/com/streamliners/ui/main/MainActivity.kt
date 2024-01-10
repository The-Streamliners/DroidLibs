package com.streamliners.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import com.streamliners.BuildConfig
import com.streamliners.base.BaseActivity
import com.streamliners.base.uiEvent.UiEventDialogs
import com.streamliners.ui.theme.DroidLibsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    override var debugMode: Boolean =
        BuildConfig.BUILD_TYPE == "debug"
}