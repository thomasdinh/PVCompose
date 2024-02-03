
package com.example.pvcompose.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.pvcompose.R
import com.example.pvcompose.data.BarData
import com.example.pvcompose.data.barChartdata
import com.example.pvcompose.data.colorList
import com.example.pvcompose.data.darkGreen
import com.example.pvcompose.data.darkRed
import com.example.pvcompose.data.darkYellow
import com.example.pvcompose.data.lightGreen
import com.example.pvcompose.data.orange
import com.example.pvcompose.device.Device
import com.example.pvcompose.device.DeviceViewModel


// Find barChartdata im Example_data.kt
@Composable
fun ReportScreen(deviceViewModel: DeviceViewModel) {


    LazyColumn(){
        item { dataCollectedProgressDisplay() }
        item { stackedBarChart(deviceViewModel) }
    }
}


@Composable
fun stackedBarChart(deviceViewModel: DeviceViewModel,
                    modifier: Modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp))
{


    var barChartDataUnedited = deviceDataAnalysis(deviceViewModel = deviceViewModel)
    Log.d("BeforeDataTransform ","Success")
    var barChartData = transformDataClassToBarData(barChartDataUnedited)
    Log.d("AfterDataTransform ","Success")
    var maxHeight : Float?= 0f
    if(barChartData.isEmpty()){
        Log.d("Missing Data", "No Data to make a bar Chart")
    }else{
        maxHeight = barChartData.maxOfOrNull { it.totalHeight }
    }

    val amountOfBars = barChartData.size

    Box {
        Column {
            Canvas(modifier = Modifier
                .padding(8.dp)
                .aspectRatio(3 / 2f)
                .fillMaxWidth()
                .height(100.dp)){
                val barWithPx = 1.dp.toPx()
                drawRect(Color.Black, style = Stroke(barWithPx))

                //Draw Background
                val horizontalLines = 4
                val sectionSize = size.height/(horizontalLines +1)
                repeat(horizontalLines){i ->
                    val startY = sectionSize * (i+1)
                    drawLine(Color.Black,
                        start = Offset(0f, startY),
                        end = Offset(size.width, startY),
                        strokeWidth = barWithPx
                    )
                }

                //Draw BarChart
                val barWidth = size.width / (amountOfBars +1)
                var currentX = 0
                val spaceFactor = 0.2f // Change this value to adjust the space
                val accBarHeight = createMutableZeroList(amountOfBars)
                for (barHeightIndex in barChartData[0].barHeights.indices) {
                    currentX = 0
                    barChartData.forEachIndexed { barIndex, barData ->
                        currentX += (barWidth).toInt()
                        val barHeight = (barData.barHeights[barHeightIndex] / maxHeight!!) * size.height
                        val accBarHeightSize = (accBarHeight[barIndex]!! / maxHeight!!) * size.height
                        drawLine(
                            barData.colors[barHeightIndex],
                            start = Offset(currentX.toFloat(), size.height - accBarHeightSize),
                            end = Offset(currentX.toFloat(), size.height - barHeight - accBarHeightSize),
                            strokeWidth = barWidth - (barWidth * spaceFactor)
                        )

                        accBarHeight[barIndex] = accBarHeight[barIndex]?.plus(barData.barHeights[barHeightIndex])
                    }
                }

            }
            Row {
                barChartData.forEach(){
                    Text(text = it.label,
                        modifier
                            .weight(1f)
                            .padding(20.dp))
                }
            }
        }

    }

}


@Composable
fun dataCollectedProgressDisplay(){
    //https://www.flaticon.com/free-icon/wave-sound_6707113?term=sound&page=1&position=2&origin=search&related_id=6707113 - Sound
    //https://www.flaticon.com/free-icon/biometric_2654206?term=biometric&page=1&position=33&origin=search&related_id=2654206 - biometric
    //https://www.flaticon.com/free-icon/master-data_5610201?term=online+data&page=1&position=22&origin=search&related_id=5610201 - online data
    //https://www.flaticon.com/free-icon/gps_8654885?related_id=8654885 -location
    //https://www.flaticon.com/free-icon/motion-sensor_9658568?related_id=9658568 - motion
    //https://stackoverflow.com/questions/64988112/how-do-i-use-color-resource-directly-in-jetpack-compose


    Column {
        CustomProgressBar( "Video Information", R.drawable.video_camera_icon,50 , Color.Red)
        CustomProgressBar( "Sound Information", R.drawable.sound_icon,70 , orange)
        CustomProgressBar( "Biometric Information", R.drawable.biometric_icon,10 , lightGreen)
        CustomProgressBar( "GPS Information", R.drawable.location_icon,30 , darkYellow)
        CustomProgressBar( "Online Information", R.drawable.master_data,90 , darkRed)
        CustomProgressBar( "Motion Information", R.drawable.motion_sensor,60 , darkGreen)
    }


}


@Composable
fun CustomProgressBar(
    title : String,
    image: Int,
    progress: Int,
    barColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .height(30.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))



            LinearProgressIndicator(
                progress = progress / 100f,
                color = barColor,
                modifier = Modifier
                    .weight(8f)
                    .height(30.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "$progress%",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
                modifier = Modifier.weight(1f)

            )

        }
    }

}


fun deviceDataAnalysis(deviceViewModel: DeviceViewModel) : List<accumultatedDeviceData> {

    val devices = deviceViewModel.devices.value.orEmpty()
    val accDeviceDataList = mutableListOf<accumultatedDeviceData>()


    var soundInformation = 0
    var videoInformation = 0
    var biometricInformation = 0
    var gpsInformation = 0
    var onlineInformation = 0
    var motionInformation = 0



    for (device in devices) {
        val existingEntry = accDeviceDataList.find { it.deviceType == device.deviceType }
        Log.d("devicelistEntry", "$device is selected")
        if (existingEntry != null) {
            Log.d("devicelistEntry", "$device found")
            // Entry already exists, increment values
            when (device.deviceType) {
                "Phone" -> {
                    existingEntry.soundInformation++
                    existingEntry.videoInformation++
                    existingEntry.biometricInformation++
                    existingEntry.gpsInformation++
                    existingEntry.onlineInformation++
                    existingEntry.motionInformation++
                }
                "Tablet" -> {
                    // Customize increment logic for Tablet
                    existingEntry.soundInformation++
                    existingEntry.onlineInformation++
                    existingEntry.motionInformation++
                    existingEntry.videoInformation++
                }
                "SmartSpeaker" -> {
                    existingEntry.soundInformation++
                    existingEntry.biometricInformation++
                    existingEntry.motionInformation++
                    existingEntry.videoInformation++
                }
                "SmartWatch" -> {
                    existingEntry.soundInformation++
                    existingEntry.biometricInformation++
                    existingEntry.gpsInformation++
                    existingEntry.onlineInformation++
                    existingEntry.motionInformation++
                    existingEntry.videoInformation++
                }
                "SmartWatch" -> {
                    existingEntry.soundInformation++
                    existingEntry.biometricInformation++
                    existingEntry.motionInformation++
                    existingEntry.videoInformation++
                }
                "Unknown" -> {
                    existingEntry.soundInformation++
                    existingEntry.biometricInformation++
                    existingEntry.motionInformation++
                    existingEntry.videoInformation++
                }
                // Add more cases for other device types
                else -> {
                    // Handle other device types
                }
            }
        } else {
            // Entry doesn't exist, add a new entry
            Log.d("devicelistEntry", "$device not  found")

            var newEntry : accumultatedDeviceData = accumultatedDeviceData(device.deviceType,0,0,0,0,0,0)
            when (newEntry.deviceType){
                "Phone" -> {
                    newEntry = accumultatedDeviceData("Phone",1,1,1,1,1,1,)
                }
                "Tablet" -> {
                    // Customize increment logic for Tablet
                    newEntry = accumultatedDeviceData("Tablet",1,0,0,1,1,1,)
                }
                "SmartSpeaker" -> {
                    newEntry = accumultatedDeviceData("SmartSpeaker",1,0,0,0,1,1,)
                }
                "SmartWatch" -> {
                    newEntry = accumultatedDeviceData("SmartWatch",1,0,1,0,0,1,)
                }
                "Unknown" -> {
                    newEntry = accumultatedDeviceData("Unknown",1,0,1,0,0,1,)               }
                // Add more cases for other device types
                else -> {
                    // Handle other device types
                }
            }
            // Add the new entry to the list
            accDeviceDataList += newEntry
        }
    }

    Log.d("accDeciceData", "$accDeviceDataList")

    return accDeviceDataList
}

data class accumultatedDeviceData(
    val deviceType : String,
    var soundInformation: Int = 0,
    var videoInformation: Int = 0,
    var biometricInformation : Int= 0,
    var gpsInformation: Int = 0,
    var onlineInformation : Int = 0,
    var motionInformation : Int = 0
){

}

fun createMutableZeroList(size: Int): MutableList<Float?> {
    return MutableList(size) { 0f }
}

fun transformDataClassToBarData(accumulatedDeviceDataList: List<accumultatedDeviceData>): List<BarData> {

    val barDataList = accumulatedDeviceDataList.map { accumulatedDeviceData ->
        BarData(
            label = accumulatedDeviceData.deviceType,
            barHeights = listOf(
                accumulatedDeviceData.soundInformation.toFloat(),
                accumulatedDeviceData.videoInformation.toFloat(),
                accumulatedDeviceData.biometricInformation.toFloat(),
                accumulatedDeviceData.gpsInformation.toFloat(),
                accumulatedDeviceData.onlineInformation.toFloat(),
                accumulatedDeviceData.motionInformation.toFloat()
            ),
            colors = colorList
        )

    }
    Log.d("DataTransform", "$barDataList")
    return barDataList
}