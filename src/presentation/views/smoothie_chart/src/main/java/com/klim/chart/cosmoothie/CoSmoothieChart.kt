package com.klim.chart.cosmoothie

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klim.chart.cosmoothie.utils.DataPreparation
import com.klim.chart.entity.ChartDataItem
import com.klim.chart.smoothie.ChartPreparedDataItem
import kotlin.math.roundToInt

class ChartData(
    val data: List<ChartDataItem>,
    val chartColor: Color,
    val chartGradientColorFrom: Color,
    val chartGradientColorTo: Color,
)

@Composable
fun CoSmoothieChart(
    data: ChartData,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier,
    ) {

        val chartPreparedData = DataPreparation.prepareData(size.width.toInt(), size.height.toInt(), data.data)
        drawTable(data, chartPreparedData)
        drawChart(data, chartPreparedData.chartData)

    }
}

private fun DrawScope.drawTable(
    data: ChartData,
    chartPreparedData: ChartPreparedData
) {
    chartPreparedData.tableData.forEach { line ->
        drawLine(
            color = Color.Black.copy(alpha = 0.5f),
            alpha = 0.5f,
            start = Offset(
                x = line.start,
                y = line.lineY,
            ),
            end = Offset(
                x = line.end,
                y = line.lineY,
            )
        )
        drawIntoCanvas {
            val paint = Paint()
                .apply {
                    color = android.graphics.Color.GRAY
                    alpha = (255 * 0.5f).roundToInt()
                    isAntiAlias = true
                    strokeWidth = 1f
                    style = Paint.Style.FILL
                    strokeCap = Paint.Cap.ROUND
                    textSize = 10.sp.toPx()
                }
            it.nativeCanvas.drawText(String.format("%.2f", line.value), line.start + 8.dp.toPx(), line.lineY - 2.dp.toPx(), paint)
        }
    }
}

private fun DrawScope.drawChart(
    data: ChartData,
    chartPreparedData: List<ChartPreparedDataItem>
) {
    val chartPath = Path()

    chartPath.reset()
    chartPath.moveTo(chartPreparedData[0].cubic0.x, chartPreparedData[0].cubic0.y)
    chartPreparedData.forEach { chartItem ->
        DataPreparation.addChartCubicPointToPath(chartPath, chartItem)
    }
    drawPath(
        path = chartPath,
        color = data.chartColor,
        style = Stroke(
            width = 2.dp.toPx()
        )
    )

    chartPath.lineTo(size.width, size.height)
    chartPath.lineTo(0f, size.height)
    chartPath.lineTo(chartPreparedData[0].cubic0.x, chartPreparedData[0].cubic0.y)

    val brush = Brush.verticalGradient(listOf(data.chartGradientColorFrom, data.chartGradientColorTo))
    drawPath(path = chartPath, brush = brush)
}

@Preview
@Composable
fun ChartPreview() {

    val data = ChartData(
        chartColor = Color.Red,
        chartGradientColorFrom = Color.Red.copy(alpha = 0.5f),
        chartGradientColorTo = Color.Transparent,
        data = listOf(
            ChartDataItem(1631246400000, 736.27f),
            ChartDataItem(1631505600000, 543f),
            ChartDataItem(1631592000000, 444.49f),
            ChartDataItem(1631678400000, 550.83f),
            ChartDataItem(1631764800000, 656.99f),
            ChartDataItem(1631851200000, 756.59f),
            ChartDataItem(1631937600000, 735f),
            ChartDataItem(1632024000000, 756f),
            ChartDataItem(1632110400000, 956.5f),
            ChartDataItem(1632196800000, 807f),
            ChartDataItem(1632283200000, 757.5f),
            ChartDataItem(1632369600000, 757f),
            ChartDataItem(1632456000000, 855f),
            ChartDataItem(1632542400000, 750f),
            ChartDataItem(1632628800000, 760f),
        )
    )

    CoSmoothieChart(
        data = data,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}