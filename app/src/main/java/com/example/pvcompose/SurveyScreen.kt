package com.example.pvcompose

// NetworkScanScreen.kt

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress

@Composable
fun SurveyScreen() {

}


fun pingSweep(subnet: String, start: Int, end: Int) {
    for (i in start..end) {
        val ipAddress = "$subnet.$i"
        try {
            val address = InetAddress.getByName(ipAddress)
            if (address.isReachable(1000)) {
                Log.d("Scan work","$ipAddress is reachable.")
                // You can update your UI here using Jetpack Compose
            }
        } catch (e: Exception) {
            // Handle exceptions (e.g., timeout, unknown host)
        }
    }
}


