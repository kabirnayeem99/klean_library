package io.github.kabirnayeem99.klean.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle


inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)


fun Activity.getScreenHeight(): Int {
    val size = Point()
    windowManager.defaultDisplay.getSize(size)
    return size.y
}

fun Activity.getScreenWidth(): Int {
    val size = Point()
    windowManager.defaultDisplay.getSize(size)
    return size.x
}