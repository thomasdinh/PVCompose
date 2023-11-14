package com.example.pvcompose

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pvcompose.ui.theme.PVComposeTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

data class NavigationItem(
    val title: String,
    val selectedIcon : ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val route: String,
    val badgeCount : Int? = null
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

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
                                composable("home") { HomeScreen() }
                                composable("scanscreen") { ScanScreen(deviceViewModel = deviceViewModel) }
                                composable("settings") { SettingsScreen()}

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

fun createNavigationItems(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "home",
            hasNews = false
        ),
        NavigationItem(
            title = "Scan",
            selectedIcon = Icons.Filled.Warning,
            unselectedIcon = Icons.Outlined.Warning,
            route = "scanscreen",
            hasNews = true
        ),
        NavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = "settings",
            hasNews = false
        )
        // Add more NavigationItem objects as needed
    )
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PVComposeTheme {

    }
}




