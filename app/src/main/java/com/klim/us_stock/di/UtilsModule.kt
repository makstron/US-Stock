package com.klim.us_stock.di

import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class UtilsModule {

    @Provides
    fun getGetGeocoder(context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }

}