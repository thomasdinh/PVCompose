package com.example.pvcompose.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pvcompose.screens.settingsViewModel
import com.example.pvcompose.data.toggleCardList


data class toggleCardsDescription(
    val title : String,
    val image: Int,
    val description : String,
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataTypeSettingScreen(
    viewModel: settingsViewModel = viewModel()
){
    var sliderValue by remember { mutableStateOf(50f) }
    val videoSwitchValue by viewModel.videoSwitchFlow.collectAsState(initial = null)
    val soundSwitchValue by viewModel.soundSwitchFlow.collectAsState(initial = null)
    val biometricSwitchValue by viewModel.biometricSwitchFlow.collectAsState(initial = null)
    val gpsSwitchValue by viewModel.gpsSwitchFlow.collectAsState(initial = null)
    val onlineSwitchValue by viewModel.onlineSwitchFlow.collectAsState(initial = null)
    val motionSwitchValue by viewModel.motionSwitchFlow.collectAsState(initial = null)


    LazyColumn(
            modifier = Modifier.padding(10.dp)
    ){
        item {
            Text(
                text = "Data Settings",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            SettingsSwitch(
                toggleCardsDescription = toggleCardList[0],
                value = videoSwitchValue,
                onCheckedChange = { isChecked -> viewModel.saveVideoSwitch(isChecked) }
            )
        }
        item {
            SettingsSwitch(
                toggleCardsDescription = toggleCardList[1],
                value = soundSwitchValue,
                onCheckedChange = { isChecked -> viewModel.saveSoundSwitch(isChecked) }
            )
        }
        item {
            SettingsSwitch(
                toggleCardsDescription = toggleCardList[2],
                value = biometricSwitchValue,
                onCheckedChange = { isChecked -> viewModel.saveBiometricSwitch(isChecked) }
            )
        }
        item {
            SettingsSwitch(
                toggleCardsDescription = toggleCardList[3],
                value = gpsSwitchValue,
                onCheckedChange = { isChecked -> viewModel.saveGpsSwitch(isChecked) }
            )
        }
        item {
            SettingsSwitch(
                toggleCardsDescription = toggleCardList[4],
                value = onlineSwitchValue,
                onCheckedChange = { isChecked -> viewModel.saveOnlineSwitch(isChecked) }
            )
        }
        item {
            SettingsSwitch(
                toggleCardsDescription = toggleCardList[5],
                value = motionSwitchValue,
                onCheckedChange = { isChecked -> viewModel.saveMotionSwitch(isChecked) }
            )
        }
    }


}


@Composable
fun SettingsSwitch(toggleCardsDescription : toggleCardsDescription, value: Boolean?, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(65.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card {

        }
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
        Column (modifier = Modifier.weight(1f)) {
            Switch(
                checked = value ?: false,
                onCheckedChange = onCheckedChange
            )
        }


    }
}
