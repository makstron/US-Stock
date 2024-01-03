package com.klim.chart.smoothie

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

internal class ChartDebug(val paintDebug: Paint) {

    private val pathDebug = Path()

    fun onDraw(canvas: Canvas, chartPreparedData: ArrayList<ChartPreparedDataItem>) {

        canvas.apply {  }

        pathDebug.reset()
        pathDebug.moveTo(chartPreparedData[0].cubic0.x, chartPreparedData[0].cubic0.y)

        chartPreparedData.forEach {
            pathDebug.lineTo(it.cubic3.x, it.cubic3.y)
        }
        canvas.drawPath(pathDebug, paintDebug)

        canvas.drawCircle(chartPreparedData[0].cubic0.x, chartPreparedData[0].cubic0.y, 10f, paintDebug)
        chartPreparedData.forEach {
            canvas.drawCircle(it.cubic3.x, it.cubic3.y, 10f, paintDebug)

            canvas.drawCircle(it.cubic1.x, it.cubic1.y, 5f, paintDebug)
            canvas.drawCircle(it.cubic2.x, it.cubic2.y, 5f, paintDebug)
        }
    }

}