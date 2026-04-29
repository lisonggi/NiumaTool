package com.song.niumatool.utils

import android.content.res.Resources
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap

fun textToImageBitmap(
    text: String,
    widthMm: Float,
    textSizeSp: Float = 18f,
    printerDpi: Int = 203 // ✅ 默认打印机 DPI
): ImageBitmap {

    // 1️⃣ mm → px（用打印机 DPI）
    val widthPx = mmToPx(widthMm, printerDpi)

    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = textSizeSp * Resources.getSystem().displayMetrics.scaledDensity
        color = android.graphics.Color.BLACK
    }

    // 2️⃣ 正确传 px
    val staticLayout = StaticLayout.Builder
        .obtain(text, 0, text.length, paint, widthPx)
        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
        .setLineSpacing(0f, 1f)
        .setIncludePad(false)
        .build()

    // 3️⃣ 创建 Bitmap
    val bitmap = createBitmap(widthPx, staticLayout.height)

    val canvas = android.graphics.Canvas(bitmap)
    staticLayout.draw(canvas)

    // 4️⃣ 转 ImageBitmap（关键）
    return bitmap.asImageBitmap()
}

fun mmToPx(mm: Float, dpi: Int): Int {
    return (mm * dpi / 25.4f).toInt()
}
fun  String.wrapMixed(maxWidth: Int = 17): String {
    val sb = StringBuilder()
    var width = 0

    for (c in this) {
        val charWidth = if (c.code > 255) 2 else 1
        width += charWidth

        if (width > maxWidth) {
            sb.append("\n")
            width = charWidth
        }
        sb.append(c)
    }
    return sb.toString()
}