package com.klim.smoothie_chart

import android.graphics.Color
import android.graphics.Paint

internal class Paints {

    public fun getMainPaint() =
        Paint().apply {
            color = Color.RED
            isAntiAlias = true
            strokeWidth = 10f
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }

    public fun getDebugPaint() =
        Paint().apply {
            color = Color.GREEN
            isAntiAlias = true
            strokeWidth = 4f
            textSize = 50f
            style = Paint.Style.STROKE
        }
}