package com.klim.analytics.di

import android.content.Context
import com.klim.analytics.AnalyticsI
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
//    dependencies = [Context::class],
    modules = [FirebaseModule::class]
)
interface AnalyticsComponent : AnalyticsDependency {

//    fun provideFirebaseCrashlytics(): FirebaseCrashlytics

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AnalyticsComponent
    }

    override fun provideAnalytics(): AnalyticsI
}