package io.github.kabirnayeem99.klean.ktx

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.AnimRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "Context"

fun Context.copyToClipboard(content: String) {
    val clipboardManager = ContextCompat.getSystemService(this, ClipboardManager::class.java)!!
    val clip = ClipData.newPlainText("clipboard", content)
    clipboardManager.setPrimaryClip(clip)
}

fun Context.isOnline(): Boolean {

    // checks if the network permission is already granted or not
    val hasNetworkPermission = hasPermissions(android.Manifest.permission.ACCESS_NETWORK_STATE)

    // if the network state permission is not granted
    // there is no point in going further, just return not connected as a state
    if (!hasNetworkPermission) return false

    var isNetworkConnected = false

    try {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager?.activeNetwork ?: return false
            val isNetworkActiveNow =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            isNetworkConnected = when {
                isNetworkActiveNow.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                isNetworkActiveNow.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                isNetworkActiveNow.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            // once we move the min sdk to 23, we can remove this deprecated code
            @Suppress("DEPRECATION")
            connectivityManager?.run {
                connectivityManager.activeNetworkInfo?.run {
                    isNetworkConnected = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
    } catch (e: Exception) {
        Log.e(TAG, "Failed to find out if network is connected or not.")
    }

    return isNetworkConnected
}

fun Context.hasPermissions(vararg permissions: String) = permissions.all { permission ->
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.drawable(@DrawableRes drawableRes: Int): Drawable? =
    AppCompatResources.getDrawable(this, drawableRes)

fun Context.customAnimation(@AnimRes enterResId: Int, @AnimRes exitResId: Int): Bundle? =
    ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId).toBundle()

fun RecyclerView.ViewHolder.string(@StringRes stringRes: Int, vararg formatArgs: Any): String =
    itemView.context.getString(stringRes, *formatArgs)

fun RecyclerView.ViewHolder.color(@ColorRes colorRes: Int): Int =
    ContextCompat.getColor(itemView.context, colorRes)

fun RecyclerView.ViewHolder.drawable(@DrawableRes drawableRes: Int): Drawable? =
    AppCompatResources.getDrawable(itemView.context, drawableRes)