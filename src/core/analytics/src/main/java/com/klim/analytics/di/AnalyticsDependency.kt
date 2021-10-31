package com.klim.analytics.di

import com.klim.analytics.AnalyticsI

interface AnalyticsDependency {
    fun provideAnalytics(): AnalyticsI
}