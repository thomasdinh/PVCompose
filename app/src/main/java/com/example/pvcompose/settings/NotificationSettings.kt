package com.example.pvcompose.settings

import android.widget.RadioGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NotificationTimingScreen() {

    var immideatlyCheck by remember { mutableStateOf(true) }
    var periodicallyCheck by remember { mutableStateOf(true) }
    var limitCheck by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text("Notifications - Timing")
        Text("When would you like to receive notifications?")

        Row {
            Checkbox( immideatlyCheck,
                onCheckedChange = {
                    immideatlyCheck = !immideatlyCheck
                })
            Text(text = "Immediately")// Immediately
        }
        Row {
            Checkbox( periodicallyCheck,
                onCheckedChange = {
                    periodicallyCheck = !periodicallyCheck
                })
            Text(text = "Periodically")// Immediately

        }

        Row {
            Checkbox( limitCheck,
                onCheckedChange = {
                    limitCheck = !limitCheck
                })
            Text(text = "On Wifi Connect")// Immediately

        }

    }
}