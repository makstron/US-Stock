package com.klim.stock.analytics.analytics

import android.os.Bundle
import androidx.annotation.Size

interface Analytics {

    fun logEvent(@Size(min = 1L,max = 40L) name: String, params: Bundle?)

}