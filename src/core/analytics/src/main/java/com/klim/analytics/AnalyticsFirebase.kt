package com.klim.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsFirebase(val firebaseAnalytics: FirebaseAnalytics): AnalyticsI {


    override fun logEvent(name: String, params: Bundle? ) {
        firebaseAnalytics.logEvent(name, params)
//        firebaseAnalytics.logEvent(FirebaseLogKeys.ACTION_OPEN_SYMBOL_DETAILS, params)

//        var firebaseCrashlytics: FirebaseCrashlytics()
//        firebaseCrashlytics.setCustomKey(FirebaseCrashKeys.SYMBOL_DETAILS, currentSymbol)
    }


}