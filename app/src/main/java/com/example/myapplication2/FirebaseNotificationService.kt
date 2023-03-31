package com.example.myapplication2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class FirebaseNotificationService: FirebaseMessagingService() {
    val TAG = "FirebaseMessagingService"

    companion object {
        const val CHANNEL_ID = "authentication_channel"
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "Dikirim dari : ${message.from}")
        Log.d(TAG, "Notification : ${message.data}")
        val notificationService = NotificationService(applicationContext)
        if (message.data.isNotEmpty()) {
            notificationService.showFirebaseNotification(message)
        }
    }

}