package com.example.pvcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class toggleCardsDescription(
    val title : String,
    val image: Int,
    val description : String,

    )

@Composable
fun DataTypeSettingScreen(){
    var sliderValue by remember { mutableStateOf(50f) }
    val toggleCardList = listOf<toggleCardsDescription>(
        toggleCardsDescription( "Video Information",R.drawable.video_camera_icon,"50 , Color.Red"),
        toggleCardsDescription( "Sound Information",R.drawable.sound_icon,"70 , orange"),
        toggleCardsDescription( "Biometric Information",R.drawable.biometric_icon,"10 , lightGreen"),
        toggleCardsDescription( "GPS Information",R.drawable.location_icon,"30 , darkYellow"),
        toggleCardsDescription( "Online Information",R.drawable.master_data,"90 , darkRed"),
        toggleCardsDescription( "Motion Information",R.drawable.motion_sensor,"60 , darkGreen")
    )
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
            items(toggleCardList){
                toggleCard(it)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }


    }
}

@Composable
fun toggleCard(toggleCardsDescription : toggleCardsDescription){
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
                var checked by remember { mutableStateOf(true) }
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    }
                )
            }
        }
    }
}