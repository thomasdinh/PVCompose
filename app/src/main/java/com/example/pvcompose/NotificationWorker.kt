package com.example.pvcompose

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    override  fun doWork(): Result {
        Log.d("Worker", "Worker initiated")
        showNotification(applicationContext, "PrivaSee", "Hey, Check your report!")
        return Result.success()
    }
}