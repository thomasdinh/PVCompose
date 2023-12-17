package com.example.pvcompose

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


data class toggleCardsDescription(
    val title : String,
    val image: Int,
    val description : String,
    )

@Composable
fun DataTypeSettingScreen(
    viewModel: settingsViewModel = viewModel()
){
    var sliderValue by remember { mutableStateOf(50f) }
    val videoSwitch = viewModel.videoSwitchFlow.collectAsState(initial = true)
    val soundSwitch = viewModel.soundSwitchFlow.collectAsState(initial = true)
    val biometricSwitch = viewModel.biometricSwitchFlow.collectAsState(initial = true)
    val gpsSwitch = viewModel.gpsSwitchFlow.collectAsState(initial = true)
    val motionSwitch = viewModel.motionSwitchFlow.collectAsState(initial = true)
    val onlineSwitch = viewModel.onlineSwitchFlow.collectAsState(initial = true)

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 0f..100f,
            steps = 101,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "Slider value is: ${sliderValue.toInt()}")

        LazyColumn(){
            // ADD Items here
            item {
               switchCard(saveBoolean = {viewModel.saveVideoSwitch(false)}, currentBoolean = videoSwitch )
            }
        }


    }
}

@Composable
fun toggleCard(toggleCardsDescription : toggleCardsDescription, isChecked : State<Boolean?>, context: Context, onToggle:(Boolean)-> Unit){
    Card {
        Row(modifier = Modifier
            .padding(20.dp)
            .height(70.dp)) {
            Column(modifier = Modifier.weight(1.5f)) {
                Image(
                    painter = painterResource(id = toggleCardsDescription.image),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                )
            }
            Column(modifier = Modifier.weight(3f)) {
                Text(text = toggleCardsDescription.title, style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold)
                Text(text ="There will be text added later on...", style = MaterialTheme.typography.bodyMedium)

            }
            Column (modifier = Modifier.weight(1f)){
                var checked by remember { mutableStateOf(isChecked.value?: true) }
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                        onToggle.invoke(it)

                    }
                )
            }
        }
    }
}

@Composable
fun switchCard(
    saveBoolean:(Boolean) -> Unit,
    currentBoolean : State<Boolean?>
){
    val isChecked = remember {
        mutableStateOf(currentBoolean.value ?: true)
    }
    Switch(checked = isChecked.value,
        onCheckedChange = { newValue ->
            isChecked.value = newValue
            saveBoolean.invoke(newValue)
        }
    )
}
