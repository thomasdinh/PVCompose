package com.example.pvcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pvcompose.R


@Composable
fun HomeScreen(navController: NavHostController){
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center){

        Column {
            Text(
                text = "HOME",
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
                val homeItems = createHomeScreenItems()
                items(homeItems){ item ->
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


data class homeScreenItems(
    val icon : Int,
    val title: String,
    val contentDescription : String,
    val route : String
)
fun createHomeScreenItems(): List<homeScreenItems> {
    return listOf<homeScreenItems>(
        homeScreenItems(
            //https://www.flaticon.com/free-icon/business-report_3094851?term=report&page=1&position=8&origin=search&related_id=3094851
            icon = R.drawable.report_icon_home ,
            title = "Report",
            contentDescription = "See here a evaluation of the found devices!",
            route = "report_screen"
        ),
        homeScreenItems(
            //https://www.flaticon.com/free-icon/cloud_4826371?term=iot&page=1&position=39&origin=search&related_id=4826371
            icon = R.drawable.scan_icon ,
            title = "Scan Devices",
            contentDescription = "A list about found devices in your network!",
            route = "scanscreen"
        ),
        homeScreenItems(
            //https://www.flaticon.com/free-icon/call-center_2706988?term=survey&page=1&position=9&origin=search&related_id=2706988
            icon = R.drawable.survey_icon ,
            title = "Survey",
            contentDescription = "A questionnaire about our product!",
            route = "survey_screen"
        ),
        homeScreenItems(
            //https://www.flaticon.com/free-icon/gear_484613?term=setting&page=1&position=10&origin=search&related_id=484613
            icon = R.drawable.settings_icon,
            title = "Settings",
            contentDescription = "Change your preferences here!",
            route = "settings"
        ),
        homeScreenItems(
            //https://www.flaticon.com/free-icon/user_4803103?term=about+us&page=1&position=20&origin=search&related_id=4803103
            icon = R.drawable.about_us_icon,
            title = "About us",
            contentDescription = "Find more about us!",
            route = "about_us"
        ),
        homeScreenItems(
            //https://www.flaticon.com/free-icon/exit-to-app-button_61208?term=close+app&page=1&position=7&origin=search&related_id=61208
            icon = R.drawable.exit_to_app_icon,
            title = "Close App",
            contentDescription = "Close the app.",
            route = "exit"
        )
    )
}
