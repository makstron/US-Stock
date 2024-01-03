package com.klim.stock.analytics.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsFirebase(val firebaseAnalytics: FirebaseAnalytics): Analytics {

    override fun logEvent(name: String, params: Bundle? ) {
        firebaseAnalytics.logEvent(name, params)
    }


}