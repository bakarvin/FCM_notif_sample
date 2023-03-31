package com.example.myapplication2.Firebase

import android.util.Log
import android.widget.Toast
import com.example.myapplication2.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseInstance : FirebaseMessagingService() {
    val TAG = "PushNotifService"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            // Log
            Log.d(TAG, token)
        })
    }
}