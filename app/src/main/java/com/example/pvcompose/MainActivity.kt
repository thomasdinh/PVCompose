package com.example.pvcompose

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.NotificationCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pvcompose.data.createNavigationItems
import com.example.pvcompose.device.DeviceViewModel
import com.example.pvcompose.screens.HomeScreen
import com.example.pvcompose.screens.ReportScreen
import com.example.pvcompose.screens.ScanScreen
import com.example.pvcompose.screens.SettingsScreen
import com.example.pvcompose.screens.settingsViewModel
import com.example.pvcompose.settings.DataTypeSettingScreen
import com.example.pvcompose.settings.NotificationSettingsScreen
import com.example.pvcompose.settings.SettingsViewModelFactory
import com.example.pvcompose.settings.SoundAndHapticsSettings
import com.example.pvcompose.settings.SurveyScreen
import com.example.pvcompose.ui.theme.PVComposeTheme

data class NavigationItem(
    val title: String,
    val selectedIcon : ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val route: String,
    val badgeCount : Int? = null
)

class MainActivity : ComponentActivity() {

    val Context.dataSettingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "data_settings")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {


        val viewModelProviderFactory = SettingsViewModelFactory(dataSettingsDataStore)
        val settingsViewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            settingsViewModel::class.java)
        val deviceViewModel: DeviceViewModel by viewModels()

        super.onCreate(savedInstanceState)
        setContent {
            PVComposeTheme {

                //------------Notification-----------------------------------------------------------
                val context = this
                //-----------Navigation-------------------------------------------------------------
                val navItems = createNavigationItems()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                //---------- UI --------------------------------------------------------------------
                    var selectedItemIndex = remember {
                        mutableStateOf(0)
                    }
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                navItems.forEachIndexed{ index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex.value == index,
                                    onClick = {
                                        showNotification(
                                            context = context, // Replace with your activity's context
                                            title = "Custom Title",
                                            message = "This is a custom message for the notification."
                                        )
                                        selectedItemIndex.value = index
                                        navController.navigate(item.route)
                                    },
                                    label = {
                                            Text(text = item.title)
                                    },
                                    icon = {
                                        BadgedBox(badge = {
                                            if(item.badgeCount != null){
                                                Badge {
                                                    Text(text = item.badgeCount.toString())
                                                }
                                            }else if (item.hasNews){
                                                Badge()
                                            }
                                        }) {
                                            Icon(
                                                imageVector = if(index == selectedItemIndex.value){
                                                    item.selectedIcon
                                                }else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        }
                                    }
                                )

                                }
                            }
                        },


                    ) { innerPadding ->
                        Box(
                            modifier = Modifier.padding(innerPadding)
                        ){
                            NavHost(navController = navController, startDestination = "home") {
                                composable(Navigation.HOME) { HomeScreen(navController = navController) }
                                composable(Navigation.SCAN_SCREEN) { ScanScreen(deviceViewModel = deviceViewModel) }
                                composable(Navigation.SETTINGS) { SettingsScreen(navController = navController) }
                                composable(Navigation.SURVEY){ SurveyScreen() }
                                composable(Navigation.REPORT){ ReportScreen() }
                                composable(Navigation.NOTIFICATION_SETTING){ NotificationSettingsScreen(navController = navController) }
                                composable(Navigation.DATATYPE_SETTING){ DataTypeSettingScreen(settingsViewModel) }
                                composable(Navigation.SOUNDHAPTICS_SETTINGS){ SoundAndHapticsSettings() }

                            }
                            navController.navigate("home")
                        }

                    }

                    
                } 
            }
        }
    }


}








