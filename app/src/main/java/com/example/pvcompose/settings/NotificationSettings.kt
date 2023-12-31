package com.example.pvcompose.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pvcompose.Navigation
import com.example.pvcompose.R


@Composable
fun NotificationSettingsScreen(navController: NavHostController){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
    Row (modifier = Modifier
        .height(80.dp)

        .fillMaxWidth()) {
        Text(text = "Notification Settings", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.weight(3f).wrapContentSize(Alignment.TopStart))
        Image(painter = painterResource(id = R.drawable.settings_icon), contentDescription = "Notification Settings", modifier = Modifier.weight(2f)
            .wrapContentSize(Alignment.TopStart))
    }

        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Adjust notification settings to your preferences",
            style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item { NotificationSettingsCard(title = "Data Types", onClick = {navController.navigate(
                Navigation.DATATYPE_SETTING
            )}) }
            item { NotificationSettingsCard(title = "Sound & Haptics",  onClick = {navController.navigate(
                Navigation.SOUNDHAPTICS_SETTINGS
            )}) }
            item { NotificationSettingsCard(title = "Notification Timing",  onClick = {navController.navigate(
                Navigation.HOME
            )}) }
        }

    }

}

@Composable
fun NotificationSettingsCard(title: String, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(30.dp))
    Row {
        Spacer(modifier = Modifier.width(10.dp).weight(1f))
        Card(
            modifier = Modifier
                .height(130.dp)
                .width(260.dp)
                .weight(4f)
                .clickable(onClick = onClick)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.width(10.dp).weight(1f))
    }
}