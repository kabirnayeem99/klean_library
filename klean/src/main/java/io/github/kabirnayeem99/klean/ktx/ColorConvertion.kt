package io.github.kabirnayeem99.klean.ktx

import android.graphics.Color

fun String.hexToRGB(): Triple<String, String, String> {
    var name = this
    if (!name.startsWith("#")) {
        name = "#$this"
    }
    val color = Color.parseColor(name)
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)

    return Triple(red.toString(), green.toString(), blue.toString())
}

fun Int.colorToHexString(): String {
    return String.format("#%06X", -0x1 and this).replace("#FF", "#")
}