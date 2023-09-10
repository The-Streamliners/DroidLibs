package com.streamliners.common.helper

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.Random

class NotificationHelper(
    private val context: Context
) {

    companion object {
        const val DEFAULT_CHANNEL = "notifications"

        fun areNotificationsEnabled(context: Context): Boolean {
            val notificationManager = NotificationManagerCompat.from(context)
            return notificationManager.areNotificationsEnabled()
        }
    }

    private fun createNotificationChannel(
        id: String = DEFAULT_CHANNEL,
        name: String = "Notifications",
        description: String = "All notifications"
    ) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        val alreadyExists = notificationManager.notificationChannels.find { it.id == id } != null
        if (alreadyExists) return

        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance)
        channel.description = description
        channel.enableVibration(true)

        notificationManager.createNotificationChannel(channel)
    }

    @SuppressLint("MissingPermission")
    fun showNotification(
        title: String,
        body: String,
        channelId: String = DEFAULT_CHANNEL,
        @DrawableRes icon: Int,
        activityClass: Class<*>,
        modifyIntent: Intent.() -> Unit = {}
    ): Int {
        if (!areNotificationsEnabled(context)) {
            error("PostNotification permission not granted!")
        }

        createNotificationChannel(channelId)

        /* PendingIntent */
        val intent = Intent(context, activityClass).apply { modifyIntent() }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT xor PendingIntent.FLAG_IMMUTABLE)

        /* Build */
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSmallIcon(icon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        /* Post */
        val notificationManager = NotificationManagerCompat.from(context)
        val id = Random().nextInt(30000) + 1000
        notificationManager.notify(id, notification)

        return id
    }
}