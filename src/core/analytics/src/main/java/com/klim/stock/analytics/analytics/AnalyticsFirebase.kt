package com.klim.stock.analytics.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsFirebase(
    private val firebaseAnalytics: FirebaseAnalytics
) : Analytics {

    override fun logEvent(key: AnalyticKeys, params: Bundle?) {
        firebaseAnalytics.logEvent(key.key, params)
    }

    override fun logEventOpenApp() {
        logEvent(AnalyticKeys.ACTION_OPEN, Bundle())
    }

    override fun logEventCloseApp() {
        logEvent(AnalyticKeys.ACTION_EXIT, Bundle())
    }

    override fun logEventOpenSymbol(symbol: String) {
        val bundle = Bundle()
        bundle.putString("symbol", symbol)
        logEvent(AnalyticKeys.ACTION_OPEN_SYMBOL_DETAILS, bundle)
    }


}