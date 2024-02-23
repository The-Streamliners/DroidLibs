package com.streamliners.feature.compose.sample

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.streamliners.base.BaseActivity
import com.streamliners.base.ext.showToast
import com.streamliners.compose.ext.noRippleClickable

@Composable
fun BaseActivity.NoRippleClickableSample() {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 150.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable {
                        showToast("Ripple click")
                    }
                    .padding(8.dp),
                text = "With Ripple",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .noRippleClickable {
                        showToast("NO Ripple click")
                    }
                    .padding(8.dp),
                text = "Without Ripple",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}