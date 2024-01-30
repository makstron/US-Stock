package com.klim.chart.cosmoothie.utils

import android.graphics.PointF
import androidx.compose.ui.graphics.Path
import com.klim.chart.cosmoothie.ChartPreparedData
import com.klim.chart.entity.ChartDataItem
import com.klim.chart.smoothie.ChartPreparedDataItem
import com.klim.chart.smoothie.PreparedTableDataItem

object DataPreparation {

    inline fun addChartCubicPointToPath(path: Path, chartItem: ChartPreparedDataItem) {
        path.cubicTo(
            chartItem.cubic1.x, chartItem.cubic1.y,
            chartItem.cubic2.x, chartItem.cubic2.y,
            chartItem.cubic3.x, chartItem.cubic3.y,
        )
    }

    fun prepareData(
        width: Int,
        height: Int, //without paddings
        data: List<ChartDataItem>
    ): ChartPreparedData {
        if (height == 0 || width == 0)
            return ChartPreparedData(
                chartData = emptyList(),
                tableData = emptyList(),
            )


//        data.forEach { it.time = -it.time } //reverse timeline
        val sortedRawData = data.sortedBy { it.time }
        val minTime = sortedRawData.first().time
        val maxTime = sortedRawData.last().time
        val timeScale = (maxTime - minTime).toFloat() / width

        val minMax = getMinMax(sortedRawData)
        val valueMin = minMax.first
        val valueMax = minMax.second
        val valueDelta = valueMax - valueMin

        val pixelsInOneValue = height / valueMax

        val listChart = ArrayList<ChartPreparedDataItem>()
        var x1 = 0f
        var y1 = 0f
        var x2 = 0f
        var y2 = 0f
        for (i in 0 until sortedRawData.size - 1) {
            val point1 = sortedRawData[i]
            val point2 = sortedRawData[i + 1]

            x1 = (point1.time - minTime) / timeScale
            y1 = height - ((point1.value) * pixelsInOneValue)
            x2 = (point2.time - minTime) / timeScale
            y2 = height - ((point2.value) * pixelsInOneValue)

            listChart.add(
                ChartPreparedDataItem(
                    PointF(x1, y1),
                    PointF((x1 + x2) / 2, y1),
                    PointF((x1 + x2) / 2, y2),
                    PointF(x2, y2),
                )
            )
        }

        return ChartPreparedData(
            chartData = listChart,
            tableData = listOf(
                PreparedTableDataItem(
                    value = valueMin,
                    lineY = height - ((valueMin) * pixelsInOneValue),
                    start = 0f,
                    end = width.toFloat(),
                ),
                PreparedTableDataItem(
                    value = valueMax,
                    lineY = height - ((valueMax) * pixelsInOneValue),
                    start = 0f,
                    end = width.toFloat(),
                ),
                PreparedTableDataItem(
                    value = sortedRawData.last().value,
                    lineY = height - ((sortedRawData.last().value) * pixelsInOneValue),
                    start = 0f,
                    end = width.toFloat(),
                )
            ),
        )
    }

    private fun getMinMax(data: List<ChartDataItem>): Pair<Float, Float> {
        var minValue = Float.MAX_VALUE
        var maxValue = Float.MIN_VALUE
        data.forEach {
            if (it.value < minValue) {
                minValue = it.value
            }
            if (it.value > maxValue) {
                maxValue = it.value
            }
        }
        return Pair(minValue, maxValue)
    }

}