package com.streamliners.common.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ShareCompat
import com.streamliners.common.model.LatLong

class IntentsHelper(private val context: Context) {

    fun call(phoneNo: String) {
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNo")
            context.startActivity(this)
        }
    }

    fun navigate(latLong: LatLong) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://maps.google.com/maps?daddr=${latLong.lat},${latLong.lng}")
        )
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Google maps not installed!", Toast.LENGTH_SHORT).show()
        }
    }

    fun browse(url: String) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    }

    fun email(emailId: String, subject: String? = null, body: String? = null) {
        Intent(
            Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailId, null)
        ).apply {
            subject?.let { putExtra(Intent.EXTRA_SUBJECT, it) }
            body?.let { putExtra(Intent.EXTRA_TEXT, it) }
            context.startActivity(
                Intent.createChooser(this, "Send email...")
            )
        }
    }

    fun whatsAppChat(phoneNo: String, message: String? = null) {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://wa.me/$phoneNo" +
                        (message?.let { "?text=$message" } ?: "")
            )
            try {
                context.startActivity(this)
            } catch (e: Exception) {
                Toast.makeText(context, "WhatsApp not installed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun shareText(text: String) {
        ShareCompat.IntentBuilder(context)
            .setType("text/plain")
            .setText(text)
            .intent
            .apply {
                if (resolveActivity(context.packageManager) != null) {
                    context.startActivity(this)
                } else {
                    Toast.makeText(context, "Unable to share text!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun openPlayStorePage(
        packageName: String
    ) {
        browse(
            "https://play.google.com/store/apps/details?id=$packageName"
        )
    }

    fun openDateTimeSettings() {
        context.startActivity(Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
    }
}