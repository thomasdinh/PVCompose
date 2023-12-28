package com.example.pvcompose

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Warning
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.G
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
        val settingsViewModel = ViewModelProvider(this, viewModelProviderFactory).get(settingsViewModel::class.java)
        val deviceViewModel: DeviceViewModel by viewModels()

        super.onCreate(savedInstanceState)
        setContent {
            PVComposeTheme {

                //------------Notification-----------------------------------------------------------
                val context = LocalContext.current
                var hasNotificationPermission by remember {
                    mutableStateOf(
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                        } else {
                            true
                        }
                    )
                }
                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted -> hasNotificationPermission = isGranted }
                )

                LaunchedEffect(true) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }



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
                                        selectedItemIndex.value = index
                                        if (hasNotificationPermission) {
                                            showNotification()
                                        }
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
                                composable(Navigation.SETTINGS) { SettingsScreen(navController = navController)}
                                composable(Navigation.SURVEY){ SurveyScreen()}
                                composable(Navigation.REPORT){ ReportScreen()}
                                composable(Navigation.NOTIFICATION_SETTING){ NotificationSettingsScreen(navController = navController)}
                                composable(Navigation.DATATYPE_SETTING){DataTypeSettingScreen(settingsViewModel)}
                                composable(Navigation.SOUNDHAPTICS_SETTINGS){ SoundAndHapticsSettings()}

                            }
                            navController.navigate("home")
                        }

                    }

                    
                } 
            }
        }
    }

    private fun showNotification(){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, "channel_id")
            .setContentText("We have detetected some devices!")
            .setContentTitle("PrivaSee - Scanresults")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        notificationManager.notify(1,notification)
    }
}






@Composable
fun GreetingPreview() {
    PVComposeTheme {

    }
}




