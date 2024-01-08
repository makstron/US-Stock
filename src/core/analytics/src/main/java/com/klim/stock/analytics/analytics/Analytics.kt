package com.klim.stock.analytics.analytics

import android.os.Bundle
import androidx.annotation.Size

interface Analytics {

    fun logEvent(name: AnalyticKeys, params: Bundle?)

    fun logEventOpenApp()

    fun logEventCloseApp()

    fun logEventOpenSymbol(symbol: String)

}