package io.github.kabirnayeem99.klean.ktx

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect

fun Bitmap.getRounded(widthPix: Int, heightPix: Int): Bitmap {
    val targetBitmap = Bitmap.createBitmap(
        widthPix, heightPix, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(targetBitmap)
    val path = Path()
    path.addCircle(
        (widthPix.toFloat() - 1) / 2, (heightPix.toFloat() - 1) / 2, Math.min(
            widthPix.toFloat(), heightPix.toFloat()
        ) / 2, Path.Direction.CCW
    )
    canvas.clipPath(path)
    canvas.drawBitmap(
        this, Rect(
            0, 0, this.width, this.height
        ), Rect(0, 0, widthPix, heightPix), null
    )
    return targetBitmap
}

