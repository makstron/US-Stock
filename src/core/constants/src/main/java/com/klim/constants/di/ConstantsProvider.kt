package com.klim.constants.di

import com.klim.constants.qualifiers.DateFormatRequest
import com.klim.constants.qualifiers.TimezoneServer
import java.text.SimpleDateFormat
import java.util.*

interface  ConstantsProvider {
    @DateFormatRequest
    fun provideRequestDateFormatter(): SimpleDateFormat

    @TimezoneServer
    fun provideServerTimezone(): TimeZone
}