package com.klim.analytics

import android.os.Bundle
import androidx.annotation.Size

interface AnalyticsI {

    fun logEvent(@Size(min = 1L,max = 40L) name: String, params: Bundle?)

}