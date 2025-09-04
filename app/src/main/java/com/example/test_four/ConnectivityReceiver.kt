// ConnectivityReceiver.kt
package com.example.test_four

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ConnectivityReceiver : BroadcastReceiver() {

    private val TAG = "ConnectivityReceiver"  // Tag for Logcat

    override fun onReceive(context: Context?, intent: Intent?) {
        val isConnected = isNetworkAvailable(context)
        if (!isConnected) {
            // Log the network status change
            Log.d(TAG, "No internet connection.")
            // Show notification only when network is lost
            showNotification(context, "Network Status", "No internet connection")
        } else {
            // Log when the network is connected (optional)
            Log.d(TAG, "Connected to the internet.")
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    private fun showNotification(context: Context?, title: String, message: String) {
        if (context == null) return

        // Check notification permission for Android 13+
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // You can't request permissions in a BroadcastReceiver.
            // Permissions should be requested from an Activity.
            return
        }

        // Create the notification channel (required for Android O and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Network Status"
            val descriptionText = "Shows whether internet connection is available"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }
}
