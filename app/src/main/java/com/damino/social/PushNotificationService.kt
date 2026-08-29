package com.damino.social

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "damino_notifications"
        private const val CHANNEL_NAME = "Damino Notifications"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title ?: remoteMessage.data["title"] ?: "Damino"
        val body = remoteMessage.notification?.body ?: remoteMessage.data["body"] ?: ""
        val url = remoteMessage.data["url"] ?: getString(R.string.launchUrl)
        createNotificationChannel()
        showNotification(title, body, url)
    }

    override fun onNewToken(token: String) {
        Log.d("Damino", "Refreshed FCM token: $token")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    private fun showNotification(title: String, body: String, url: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            data = Uri.parse(url)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        getSystemService(NotificationManager::class.java).notify(System.currentTimeMillis().toInt(), notification)
    }
}
