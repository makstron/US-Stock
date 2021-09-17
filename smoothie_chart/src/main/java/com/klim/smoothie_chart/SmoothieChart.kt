package com.klim.smoothie_chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.View.MeasureSpec

class SmoothieChart : View {

    private var chartRawData = ArrayList<ChartDataItem>()
    private var chartPreparedData = ArrayList<ChartPreparedDataItem>()

    val p = Paint()

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
            val data = ArrayList<ChartDataItem>()
            data.add(ChartDataItem(1631246400000, 30f))
            data.add(ChartDataItem(1631505600000, 00f))
            data.add(ChartDataItem(1631592000000, 60f))
            data.add(ChartDataItem(1631678400000, 30f))
            data.add(ChartDataItem(1631764800000, 30f))
            setData(data, Color.RED)
        }

        p.color = Color.RED
        p.isAntiAlias = true
        p.strokeWidth = 10f
        p.style = Paint.Style.STROKE

    }

    fun setData(data: List<ChartDataItem>, color: Int) {
        chartRawData = ArrayList(data)
        p.color = color
        chartPreparedData = prepareData(data)
        invalidate()
    }

    private fun prepareData(data: List<ChartDataItem>): ArrayList<ChartPreparedDataItem> {
        if (_height != 0) {
            val sortedRawData = data.sortedBy { it.time }
            val minTime = sortedRawData.first().time
            val maxTime = sortedRawData.last().time
            var minValue = Float.MAX_VALUE
            var maxValue = 0f
            var valueDelta = 0f

            sortedRawData.forEach {
                if (it.value < minValue) {
                    minValue = it.value
                }
                if (it.value > maxValue) {
                    maxValue = it.value
                }
            }
            valueDelta = maxValue - minValue

            val allowedHeight = _height - paddingTop - paddingBottom
            val pixelsInOneValue = allowedHeight / valueDelta


            return sortedRawData.map {
                ChartPreparedDataItem(
                    timePosX = (it.time - minTime) / 1000000f / 2f, //295 / 2
                    valuePosY = _height - (paddingBottom + it.value * pixelsInOneValue),
                )
            } as ArrayList<ChartPreparedDataItem>
        } else {
            return ArrayList<ChartPreparedDataItem>()
        }
    }

    private var _width = 0
    private var _height = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = layoutParams.width
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            _width = widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            _width = Math.min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            _width = desiredWidth
        }

        val desiredHeight = layoutParams.height
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            _height = heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            _height = Math.min(desiredHeight, heightSize)
        } else {
            _height = desiredHeight
        }

        //mini map

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }


//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        Log.v("Chart onMeasure w", MeasureSpec.toString(widthMeasureSpec))
//        Log.v("Chart onMeasure h", MeasureSpec.toString(heightMeasureSpec))
//        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
//        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom
//        setMeasuredDimension(
//            measureDimension(desiredWidth, widthMeasureSpec),
//            measureDimension(desiredHeight, heightMeasureSpec)
//        )
//        prepareData(chartRawData)
//    }
//
    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        if (result < desiredSize) {
            Log.e("ChartView", "The view is too small, the content might get cut")
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (chartPreparedData.isEmpty() && chartRawData.isNotEmpty()) {
            chartPreparedData = prepareData(chartRawData)
        }

        canvas.drawCircle(100f, 100f, 100f, p)

        if (chartPreparedData.isNotEmpty()) {
            val path = Path()
            path.moveTo(chartPreparedData[0].timePosX, chartPreparedData[0].valuePosY)
            for (i in 1 until chartPreparedData.size) {
                path.lineTo(chartPreparedData[i].timePosX, chartPreparedData[i].valuePosY)
//            path.cubicTo()
            }
//            path.close()
            canvas.drawPath(path, p)
        }
    }
}