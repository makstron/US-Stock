package com.klim.stock.analytics.crashliytics

import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashlyticsFirebase(private val crashlytics: FirebaseCrashlytics): Crashlytics {

    override fun setCustomKey(key: String, value: String) {
        crashlytics.setCustomKey(key, value)
    }

    override fun recordException(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

}