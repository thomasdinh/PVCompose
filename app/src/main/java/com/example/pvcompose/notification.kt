package com.example.pvcompose

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

fun showNotification(context: Context, title: String, message: String) {
    // Create a NotificationManager
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Define a notification channel for devices running Android Oreo (API 26) and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "channel_id",
            "Channel Name",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    // Create a NotificationCompat.Builder
    val builder = NotificationCompat.Builder(context, "channel_id")
        .setSmallIcon(R.drawable.scan_icon) // Set your notification icon
        .setContentTitle(title) // Set the title of the notification
        .setContentText(message) // Set the message of the notification
        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the priority of the notification

    // Show the notification
    notificationManager.notify(/* notificationId */ 1, builder.build())
}