package com.klim.chart.smoothie

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.setPadding
import com.klim.chart.entity.ChartDataItem

class SmoothieChartView : View {

    //paints
    private val paints = Paints()
    private val paintChart = paints.getMainPaint()

    //debug
    private val enableDebugging = false
    private val debug by lazy { ChartDebug(paints.getDebugPaint()) }

    private var chartRawData = ArrayList<ChartDataItem>()
    internal var chartPreparedData = ArrayList<ChartPreparedDataItem>()

    //for shift
    private var startedMove = false
    private var shiftStartPositionX = 0f
    private var shiftSum = 0f
    private var shiftCurrentMove = 0f
    private var maxEnabledShift = 0f

    //drawing
    private val chartPath = Path()

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (isInEditMode) {
            setPadding(50)
            val data = ArrayList<ChartDataItem>()
            data.add(ChartDataItem(1631246400000, 736.27f))
            data.add(ChartDataItem(1631505600000, 743f))
            data.add(ChartDataItem(1631592000000, 744.49f))
            data.add(ChartDataItem(1631678400000, 750.83f))
            data.add(ChartDataItem(1631764800000, 756.99f))
            data.add(ChartDataItem(1631851200000, 756.59f))
            data.add(ChartDataItem(1631937600000, 755f))
            data.add(ChartDataItem(1632024000000, 756f))
            data.add(ChartDataItem(1632110400000, 756.5f))
            data.add(ChartDataItem(1632196800000, 757f))
            data.add(ChartDataItem(1632283200000, 757.5f))
            data.add(ChartDataItem(1632369600000, 757f))
            data.add(ChartDataItem(1632456000000, 755f))
            data.add(ChartDataItem(1632542400000, 750f))
            data.add(ChartDataItem(1632628800000, 760f))
            setData(data, Color.RED)
        }
    }

    fun setData(data: List<ChartDataItem>, color: Int) {
        chartRawData = ArrayList(data)
        paintChart.color = color
        chartPreparedData = prepareData(data)
        invalidate()
    }

    private val timeScale = 1_000_000f

    private fun prepareData(data: List<ChartDataItem>): ArrayList<ChartPreparedDataItem> {
        if (height == 0 || width == 0)
            return ArrayList()
//        data.forEach { it.time = -it.time } //reverse timeline
        val sortedRawData = data.sortedBy { it.time }
        val minTime = sortedRawData.first().time
        val maxTime = sortedRawData.last().time

        val minMax = getMinMax(sortedRawData)
        val valueMin = minMax.first
        val valueMax = minMax.second
        val valueDelta = valueMax - valueMin

        val allowedHeight = height - paddingTop - paddingBottom
        val pixelsInOneValue = allowedHeight / valueDelta

        val list = ArrayList<ChartPreparedDataItem>()
        var x1 = 0f
        var y1 = 0f
        var x2 = 0f
        var y2 = 0f
        for (i in 0 until sortedRawData.size - 1) {
            val point1 = sortedRawData[i]
            val point2 = sortedRawData[i + 1]

            x1 = paddingLeft + (point1.time - minTime) / timeScale
            y1 = height - (paddingBottom + (point1.value - valueMin) * pixelsInOneValue)
            x2 = paddingLeft + (point2.time - minTime) / timeScale
            y2 = height - (paddingBottom + (point2.value - valueMin) * pixelsInOneValue)

            list.add(
                ChartPreparedDataItem(
                    PointF(x1, y1),
                    PointF((x1 + x2) / 2, y1),
                    PointF((x1 + x2) / 2, y2),
                    PointF(x2, y2),
                )
            )
        }

        maxEnabledShift = list.last().cubic3.x - list.first().cubic0.x
        maxEnabledShift = -maxEnabledShift + width - paddingRight - paddingLeft
        shiftSum = maxEnabledShift //navigate to the right side

        return list
    }

    private fun getMinMax(data: List<ChartDataItem>): Pair<Float, Float> {
        var minValue = Float.MAX_VALUE
        var maxValue = 0f
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 200
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        var widthTemp = 0
        var heightTemp = 0

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            widthTemp = widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            widthTemp = Math.min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            widthTemp = desiredWidth
        }

        val desiredHeight = 200
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            heightTemp = heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            heightTemp = Math.min(desiredHeight, heightSize)
        } else {
            heightTemp = desiredHeight
        }

        setMeasuredDimension(widthTemp, heightTemp)
    }


    private inline fun correctXwithShift(x: Float): Float {
        return x + shiftSum + shiftCurrentMove
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (chartPreparedData.isEmpty() && chartRawData.isNotEmpty()) {
            chartPreparedData = prepareData(chartRawData)
        }

        if (chartPreparedData.isNotEmpty()) {
            chartPath.reset()
            chartPath.moveTo(correctXwithShift(chartPreparedData[0].cubic0.x), chartPreparedData[0].cubic0.y)
            chartPreparedData.forEach { chartItem ->
                addChartCubicPointToPath(chartPath, chartItem)
            }
            canvas.drawPath(chartPath, paintChart)

            if (enableDebugging) {
                debug.onDraw(canvas, chartPreparedData)
            }
        }
    }

    private inline fun addChartCubicPointToPath(path: Path, chartItem: ChartPreparedDataItem) {
        path.cubicTo(
            correctXwithShift(chartItem.cubic1.x), chartItem.cubic1.y,
            correctXwithShift(chartItem.cubic2.x), chartItem.cubic2.y,
            correctXwithShift(chartItem.cubic3.x), chartItem.cubic3.y,
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startedMove = true
                shiftStartPositionX = event.x
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                shiftCurrentMove = event.x - shiftStartPositionX
                when {
                    shiftSum + shiftCurrentMove >= 0 -> {
                        shiftSum = 0f
                        shiftCurrentMove = 0f
                    }
                    shiftSum + shiftCurrentMove <= maxEnabledShift -> {
                        shiftSum = maxEnabledShift
                        shiftCurrentMove = 0f
                    }
                    else -> {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
                invalidate()
                return true
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                startedMove = false
                shiftSum += shiftCurrentMove
                shiftCurrentMove = 0f
                return true
            }
            else -> return super.onTouchEvent(event)
        }

    }
}