package com.klim.stock.analytics.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.klim.stock.analytics.analytics.Analytics
import com.klim.stock.analytics.analytics.AnalyticsFirebase
import com.klim.stock.analytics.crashliytics.Crashlytics
import com.klim.stock.analytics.crashliytics.CrashlyticsFirebase
import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module(
    includes = [
        CreateAnalyticsModule::class,
        AnalyticsModule::class,
    ]
)
interface AnalyticsBindModule {

    @Binds
    @IntoMap
    @DependencyKey(AnalyticsProvider::class)
    fun bindProvider(impl: AnalyticsProvider): Dependency
}

@Module
class AnalyticsModule {

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideAnalytics(firebaseAnalytics: FirebaseAnalytics): Analytics {
        return AnalyticsFirebase(firebaseAnalytics)
    }


    @Provides
    @Singleton
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    @Provides
    @Singleton
    fun provideCrashlytics(crashlytics: FirebaseCrashlytics): Crashlytics {
        return CrashlyticsFirebase(crashlytics)
    }
}

@Module
class CreateAnalyticsModule {

    @Provides
    @Singleton
    fun provideAnalytics(analytics: Analytics, crashlytics: Crashlytics): AnalyticsProvider {
        return AnalyticsProviderImpl(analytics, crashlytics)
    }

}

interface AnalyticsProvider : Dependency {
    fun provideAnalytics(): Analytics
    fun provideCrashlytics(): Crashlytics
}

class AnalyticsProviderImpl(val analytics: Analytics, val crashlytics: Crashlytics) : AnalyticsProvider {
    override fun provideAnalytics(): Analytics = analytics

    override fun provideCrashlytics(): Crashlytics = crashlytics

}