package com.klim.constants.di

import com.klim.constants.NetworkConstants.REQUEST_DATE_FORMAT
import com.klim.constants.NetworkConstants.SERVER_TIMEZONE
import com.klim.constants.qualifiers.DateFormatRequest
import com.klim.constants.qualifiers.TimezoneServer
import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
class ConstantsNetworkModule {

    @Provides
    @Singleton
    @DateFormatRequest
    fun provideRequestDateFormatter(@TimezoneServer timeZone: TimeZone): SimpleDateFormat {
        return SimpleDateFormat(REQUEST_DATE_FORMAT).apply {
            this.timeZone = timeZone
        }
    }

    @Provides
    @Singleton
    @TimezoneServer
    fun provideServerTimezone(): TimeZone {
        return TimeZone.getTimeZone(SERVER_TIMEZONE)
    }

}