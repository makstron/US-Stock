package com.klim.us_stock.di

import android.content.Context
import android.location.Geocoder
import com.google.i18n.phonenumbers.PhoneNumberUtil
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    fun getGetGeocoder(context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }

    @Provides
    @Singleton
    fun getPhoneNumberUtil(): PhoneNumberUtil {
        return PhoneNumberUtil.getInstance()
    }

}