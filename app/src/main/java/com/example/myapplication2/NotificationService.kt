package com.example.myapplication2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class NotificationService(
    private val context: Context
) {
    private val notificationID = Random.nextInt()
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @RequiresApi(Build.VERSION_CODES.O)
    fun showFirebaseNotification(
        message: RemoteMessage
    ) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotifChannel(notificationManager)
        }

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_ONE_SHOT)
        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(message.notification!!.title)
            .setContentText("${message.data["merchant"]} Rp.${message.data["nominal"]}")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationID, notif.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotifChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            FirebaseNotificationService.CHANNEL_ID,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications Channel"
            enableLights(true)
        }
        try {
            notificationManager.createNotificationChannel(channel)
        } catch (e: Exception) {
            Log.d("Error Create Notif Channel : ", e.toString())
        }

    }


    companion object {
        const val CHANNEL_ID = "authentication_channel"
    }
}