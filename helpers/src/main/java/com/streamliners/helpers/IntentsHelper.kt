package com.streamliners.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat

@Composable
fun rememberIntentsHelper(): IntentsHelper {
    val context = LocalContext.current
    return remember {
        IntentsHelper(context)
    }
}

class IntentsHelper(private val context: Context) {

    fun dial(contactNo: String) {
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$contactNo")
            context.startActivity(this)
        }
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

    fun whatsAppChat(phoneNo: String, message: String?) {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.Builder()
                .scheme("https")
                .authority("wa.me")
                .appendPath(phoneNo)
                .let { builder ->
                    message?.let { builder.appendQueryParameter("text", it) } ?: builder
                }
                .build()
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
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
    }

    fun browse(url: String) {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            try {
                context.startActivity(this)
            } catch (e: Exception) {
                Toast.makeText(context, "No browser installed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun browseUsingCustomTab(url: String) {
        CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(
                CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(0xFFD30430.toInt())
                    .build()
            )
            .setShowTitle(true)
            .build()
            .launchUrl(context, Uri.parse(url))
    }

    fun openDateTimeSettings() {
        context.startActivity(Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
    }

}