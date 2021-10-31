package com.klim.constants.di

import com.klim.constants.qualifiers.DateFormatRequest
import com.klim.constants.qualifiers.TimezoneServer
import dagger.Component
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ConstantsNetworkModule::class]
)
interface ConstantsComponent: ConstantsProvider {


    @DateFormatRequest
    override fun provideRequestDateFormatter(): SimpleDateFormat


    @TimezoneServer
    override fun provideServerTimezone(): TimeZone

}