package com.example.pvcompose.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundAndHapticsSettings() {

    val sound = remember { mutableStateOf("Pulse") }
    val vibration = remember { mutableStateOf("Accent") }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Sound",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

            }
            item {
                Card(modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(26.dp),
                    ) {
                    Column {
                        soundSettingsLine("Video")
                        soundSettingsLine("Audio")
                        soundSettingsLine("Location")
                        soundSettingsLine("Motion")
                        soundSettingsLine("Biometric Data")
                        soundSettingsLine("Online Data")
                    }

                }

            }
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Vibration",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Card(modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),) {

                    Column {
                        soundSettingsLine("Video")
                        soundSettingsLine("Audio")
                        soundSettingsLine("Location")
                        soundSettingsLine("Motion")
                        soundSettingsLine("Biometric Data")
                        soundSettingsLine("Online Data")
                    }

                }

            }
        }

}

@Composable
fun soundSettingsLine(settingsTitle: String) {
    // State to control the visibility of the dialog
    var showDialog by remember { mutableStateOf(false) }

    // State to hold the text for the placeholder
    var placeholderText by remember { mutableStateOf("Placeholder") }

    // State to track the selected option index
    var selectedOptionIndex by remember { mutableStateOf(-1) }

    var soundOptions = listOf("Option 1", "Option 2", "Option 3")
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDialog = true
                }
        ) {
            Text(text = settingsTitle, modifier = Modifier
                .weight(1f)
                .padding(12.dp),
                fontWeight = FontWeight.Bold
            )
            Text(text = placeholderText, modifier = Modifier
                .weight(1f)
                .padding(12.dp))
        }

        Spacer(modifier = Modifier.height(8.dp)) // Add space between items
        Divider(color = Color.Gray, thickness = 1.dp) // Add a gray line
    }


    // Display the AlertDialog composable when showDialog is true
    if (showDialog) {
        AlertDialogWithScrollableList(
            title = "Choose an option for $settingsTitle",
            options = soundOptions,
            selectedOptionIndex = selectedOptionIndex,
            onOptionSelected = { index ->
                // Update the placeholder text based on the selected option
                placeholderText = soundOptions[index]
                selectedOptionIndex = index
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
fun AlertDialogWithScrollableList(
    title: String,
    options: List<String>,
    selectedOptionIndex: Int,
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
                Text(text = title, fontWeight = FontWeight.Bold)

                LazyColumn {
                    itemsIndexed(options) { index, option ->

                        var alertDialogcolor = if (index == selectedOptionIndex) Color.Gray else Color.Transparent
                        Text(
                            text = option,
                            modifier = Modifier
                                .clickable {
                                    onOptionSelected(index)
                                }
                                .padding(8.dp)
                                .width(200.dp)
                                .background(
                                    alertDialogcolor,
                                    shape = RoundedCornerShape(16.dp)
                                )
                        )
                    }
                }

                Button(
                    content = { Text(text = "Confirm") },
                    onClick = { onConfirm() }
                )
            }
        }
    }
}


