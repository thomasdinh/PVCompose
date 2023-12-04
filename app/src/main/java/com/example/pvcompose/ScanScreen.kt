package com.example.pvcompose

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pvcompose.DeviceViewModel


@Composable
fun ScanScreen(deviceViewModel: DeviceViewModel) {

    val deviceList = listOf<Device>(
        Device(1,"asd","asd","asd","asd"),
        Device(12,"123asd","a213sd","a22sd","a123sd")
    )


    if (deviceViewModel.isEmpty()){
        deviceViewModel.setDevices(deviceList)
    }


    deviceListScreen(deviceViewModel)

    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun deviceInfoScan(device : Device) {

    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .width(300.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.phone_vector),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = device.hostname,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = device.model,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = device.brand,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = device.deviceType,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(2.dp))


            }

        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun deviceListScreen(
    viewModel: DeviceViewModel = viewModel()
) {
    val deviceList: List<Device> by viewModel.devices.observeAsState(emptyList())
    var isDialogVisible by remember { mutableStateOf(false) }
    var editedDeviceIndex by remember { mutableStateOf(-1) } // Track the index of the edited device
    var newName by remember { mutableStateOf("") }
    var newModel by remember { mutableStateOf("") }
    var newBrand by remember { mutableStateOf("") }
    var newDeviceType by remember { mutableStateOf("") }

    LazyColumn() {
        items(deviceList.size) { index ->
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                deviceInfoScan(device = deviceList[index])
                Button(onClick = {
                    editedDeviceIndex = index
                    newName = deviceList[index].hostname
                    newModel = deviceList[index].model
                    newBrand = deviceList[index].brand
                    newDeviceType = deviceList[index].deviceType
                    isDialogVisible = true
                }) {
                    Text(text = "Edit")
                }
            }
        }
    }
    if (isDialogVisible) {
        AlertDialog(
            onDismissRequest = {
                isDialogVisible = false
                editedDeviceIndex = -1
            },
            title = { Text("Edit Device") },
            confirmButton = {
                Button(onClick = {
                    val updatedDevice = deviceList[editedDeviceIndex].copy(
                        hostname = newName,
                        model = newModel,
                        brand = newBrand,
                        deviceType = newDeviceType
                    )

                    val updatedDeviceList = deviceList.toMutableList().apply {
                        set(editedDeviceIndex, updatedDevice)
                    }
                    logData("Edit Device","Button Click")
                    viewModel.setDevices(updatedDeviceList)
                    println(viewModel.devices)
                    isDialogVisible = false
                    editedDeviceIndex = -1
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = {
                    logData("Edit Device Dismiss","Button Click")
                    isDialogVisible = false
                    editedDeviceIndex = -1
                }) {
                    Text("Cancel")
                }
            },
            text = {
                Column {

                    TextField(
                        value = newModel,
                        onValueChange = { newModel = it },
                        label = { Text("Model") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    )
                    TextField(
                        value = newBrand,
                        onValueChange = { newBrand = it },
                        label = { Text("Brand") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    )
                    TextField(
                        value = newDeviceType,
                        onValueChange = { newDeviceType = it },
                        label = { Text("Device Type") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    )
                }
            }
        )


    }
}

fun logData(actionType : String, description :String){
    val currentTime = System.currentTimeMillis()
    Log.d("$description", "Time: $currentTime, Action: $actionType")
}
