package io.github.kabirnayeem99.klean.ktx


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.*

private const val TAG = "FragmentUtility"

@SuppressLint("QueryPermissionsNeeded")
fun Fragment.showImagePicker(title: String, requestCode: Int): Result<Unit> {
    return kotlin.runCatching {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.flags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
        intent.type = "image/*"

        context?.packageManager?.let { packageManager ->
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(
                    Intent.createChooser(intent, title),
                    requestCode
                )
            } else {
                throw Error("Failed to open image picker")
            }
        }
    }
}


fun Fragment.openDialer(phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    } catch (e: Exception) {
        Log.e(TAG, e.localizedMessage ?: "")
        showToastMessage(e.localizedMessage ?: "Failed to open dialer.")
    }
}


fun Fragment.openEmailClient(
    subject: String,
    mailBody: String,
    emailTo: String
) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        Log.d(TAG, "subject -> $subject, mail body -> $mailBody and email address -> $emailTo")
        val data = Uri.parse("mailto:$emailTo?subject=" + Uri.encode(subject))
        intent.data = data
        startActivity(intent)
    } catch (e: Exception) {
        Log.e(TAG, "Failed to open email client -> ${e.localizedMessage}")
        showToastMessage(e.localizedMessage ?: "Failed to open email client.")
    }
}

fun Fragment.openMap(address: String) {
    try {
        val uri = java.lang.String.format(Locale.ENGLISH, "geo:0,0?q=$address")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    } catch (e: Exception) {
        Log.e(TAG, "Failed to open map client -> ${e.localizedMessage}")
        showToastMessage(e.localizedMessage ?: "Failed to open $address.")
    }
}

fun Fragment.openWebView(url: String) {
    try {
        val browserIntent =
            Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
        browserIntent.data = Uri.parse(url)
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(browserIntent)
    } catch (e: Exception) {
        Log.e(TAG, "Failed to open web view on a external app -> ${e.localizedMessage}")
        showToastMessage(e.localizedMessage ?: "Failed to open $url.")
    }
}

fun Fragment.showToastMessage(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
