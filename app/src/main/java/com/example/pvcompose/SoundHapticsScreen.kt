package com.example.pvcompose

import android.widget.RadioGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundAndHapticsSettings() {

    val sound = remember { mutableStateOf("Pulse") }
    val vibration = remember { mutableStateOf("Accent") }


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Sound",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                soundSettingsLine("Video")

            }
            item {
                Text(
                    text = "Vibration",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                soundSettingsLine("Video")
            }
        }

}

@Composable
fun soundSettingsLine(settingsTitle: String) {
    // State to control the visibility of the dialog
    var showDialog by remember { mutableStateOf(false) }

    // State to track the selected radio button
    var selectedOption by remember { mutableStateOf(0) }

    var soundOptions = listOf<String>("Option 1", "Option 2", "Option 3")

    // State to hold the text for the placeholder
    var placeholderText by remember { mutableStateOf("Placeholder") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDialog = true
            }
    ) {
        Text(text = settingsTitle, modifier = Modifier.weight(1f))
        Text(text = placeholderText, modifier = Modifier.weight(1f))
    }

    // Display the AlertDialog composable when showDialog is true
    if (showDialog) {
        AlertDialogWithRadioGroup(
            title = "Choose an option",
            options = soundOptions,
            selectedOption = selectedOption,
            onOptionSelected = {
                    index ->
                selectedOption = index
                // Update the placeholder text based on the selected option
                placeholderText = "Selected: ${soundOptions[index]}"
            },
            onDismissRequest = {
                showDialog = false
            },
            onConfirm = {
                // TODO: Add logic to handle the selected option
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogWithRadioGroup(
    title: String,
    options: List<String>,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = title)

                options.forEachIndexed { index, option ->
                    RadioButton(
                        selected = index == selectedOption,
                        onClick = { onOptionSelected(index) }
                    )
                    Text(text = option)
                }

                Button(
                    content = { Text(text = "Confirm") },
                    onClick = { onConfirm() }
                )
            }
        }
    }
}



