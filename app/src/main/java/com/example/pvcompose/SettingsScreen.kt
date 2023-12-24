package com.example.pvcompose

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import java.lang.Math.cos
import java.lang.Math.sin

@Composable
fun SettingsScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center){

        Column {
            Text(
                text = "SETTINGS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()

            )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(35.dp),

                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                columns = GridCells.Fixed(2)
            ){
                val settingItems = createSettingsScreenItems()
                items(settingItems){ item ->
                    Column {
                        Image(painter = painterResource(id = item.icon),
                            contentDescription = item.contentDescription,
                            modifier = Modifier.size(120.dp)
                                .clickable {
                                    navController.navigate(item.route)
                                }
                        )
                        Text(text = item.title,
                            Modifier.width(120.dp),
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Center)
                    }

                }

            }
        }

    }

}

fun createSettingsScreenItems(): List<homeScreenItems> {
    return listOf<homeScreenItems>(
        homeScreenItems(
            //https://www.flaticon.com/free-icon/setting_11377670?term=notification+settings&page=1&position=1&origin=search&related_id=11377670
            icon = R.drawable.notification_setting_icon ,
            title = "Notification Settings",
            contentDescription = "See here a evaluation of the found devices!",
            route = "notification_screen"
        ),
        homeScreenItems(
            icon = R.drawable.notification_setting_icon ,
            title = "Notification Timing",
            contentDescription = "See here a evaluation of the found devices!",
            route = "timing_screen"
        ),
        homeScreenItems(
            icon = R.drawable.notification_setting_icon ,
            title = "Notification Settings",
            contentDescription = "See here a evaluation of the found devices!",
            route = "notification_screen"
        )

    )
}






