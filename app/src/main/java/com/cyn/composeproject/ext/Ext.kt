package com.cyn.composeproject.ext

import android.annotation.SuppressLint
import android.content.res.Resources
import com.cyn.composeproject.AppApplication
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Int.toPx(): Int {
    val scale = AppApplication.getInstance().resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

/**
 * 数字简略显示文本
 */
internal fun Long.simpleNumText(): String {
    return when (this) {
        in 0..1_0000 -> this.toString()
        in 1_0000..1_0000_0000 -> "${DecimalFormat("0.##").format(this.toDouble() / 1_0000f)}万"
        else -> "${DecimalFormat("0.##").format(this.toFloat() / 1_0000_0000f)}亿"
    }
}

@SuppressLint("SimpleDateFormat")
fun formatDateMsByMS(milliseconds: Long): String {
    val simpleDateFormat = SimpleDateFormat("mm:ss")
    return simpleDateFormat.format(Date(milliseconds))
}

fun getScreenWidth(): Int = Resources.getSystem().displayMetrics.widthPixels

fun getScreenHeight(): Int = Resources.getSystem().displayMetrics.heightPixels


