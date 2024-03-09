package com.streamliners.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
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

        @Composable
        fun PermissionsSetup() {

            val context = LocalContext.current

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { granted ->
                        if (!granted) {
                            Toast.makeText(context, "Notifications won't be shown!", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                LaunchedEffect(Unit) {
                    if (areNotificationsEnabled(context)) return@LaunchedEffect

                    launcher.launch(
                        android.Manifest.permission.POST_NOTIFICATIONS
                    )
                }
            }
        }
    }

    private fun createNotificationChannel(
        id: String = DEFAULT_CHANNEL,
        name: String = "All Notifications",
        description: String = "Default channel for all notifications"
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)

            val alreadyExists = notificationManager.notificationChannels.find { it.id == id } != null
            if (alreadyExists) return

            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance)
            channel.description = description
            channel.enableVibration(true)

            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun showNotification(
        title: String,
        body: String,
        channelId: String = DEFAULT_CHANNEL,
        pendingIntentActivity: Class<*>,
        modifyNotification: NotificationCompat.Builder.() -> Unit = {},
        modifyIntent: Intent.() -> Unit = {}
    ): Int {
        if (!areNotificationsEnabled(context)) {
            error("PostNotification permission not granted!")
        }

        createNotificationChannel(channelId)

        /* Post */
        val id = randomId()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(
            id,
            buildNotification(title, body, channelId, pendingIntentActivity, modifyNotification, modifyIntent)
        )
        return id
    }

    @SuppressLint("MissingPermission")
    fun buildNotification(
        title: String,
        body: String,
        channelId: String = DEFAULT_CHANNEL,
        pendingIntentActivity: Class<*>,
        modifyNotification: NotificationCompat.Builder.() -> Unit = {},
        modifyIntent: Intent.() -> Unit = {}
    ): Notification {
        /* PendingIntent */
        val intent = Intent(context, pendingIntentActivity).apply { modifyIntent() }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT xor PendingIntent.FLAG_IMMUTABLE)

        /* Build */
        return NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSmallIcon(R.drawable.ic_notifications)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .apply(modifyNotification)
            .build()
    }

    fun randomId(): Int = Random().nextInt(30000) + 1000

    fun hideNotification(
        id: Int
    ) {
        NotificationManagerCompat.from(context).cancel(id)
    }
}