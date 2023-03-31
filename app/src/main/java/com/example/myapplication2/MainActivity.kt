package com.example.myapplication2

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.myapplication2.Firebase.MyFirebaseInstance

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val notificationService = NotificationService(applicationContext)

        MyFirebaseInstance().getToken()
    }

//    fun showNotification() {
//        val i = Intent(context, MainActivity::class.java)
//        val iPending = PendingIntent.getActivity(
//            context,
//            1,
//            i,
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
//        )
//        val incIntent = PendingIntent.getBroadcast(
//            context,
//            2,
//            Intent(context, NotificationReceiver::class.java),
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
//        )
//        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setContentTitle("MyApp Notification")
//            .setContentText("This is a sample notification from MyApp.")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(iPending)
//            .setAutoCancel(true)
//            .addAction(
//                R.drawable.ic_launcher_background,
//                "INI",
//                incIntent
//            )
//            .addAction(
//                R.drawable.ic_launcher_foreground,
//                "ITU",
//                incIntent
//            )
//            .build()
//        notificationManager.notify(
//            1, notif
//        )
//    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}