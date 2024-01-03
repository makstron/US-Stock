package com.klim.stock.utils.geocoder.di

import android.content.Context
import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyKey
import com.klim.stock.utils.geocoder.GeocoderImpl
import com.klim.stock.utils.geocoder.api.Geocoder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Module(includes = [CreateGeocoderModule::class])
interface GeocoderModule {

    @Binds
    fun bindImplementationToInterface(implementation: GeocoderImpl): Geocoder

    @Binds
    fun bindProviderImplementationToInterface(implementation: GeocoderProviderImpl): GeocoderProvider

    @Binds
    @IntoMap
    @DependencyKey(GeocoderProvider::class)
    fun bindProvider(impl: GeocoderProvider): Dependency
}

interface GeocoderProvider : Dependency {

    fun provide(): Geocoder

}

@Module
class CreateGeocoderModule {

    @Provides
    @Singleton
    fun provideGeocoder(context: Context): GeocoderImpl {
        return GeocoderImpl(context, Locale.getDefault())
    }

}

class GeocoderProviderImpl
@Inject
constructor(
    private val geocoder: Geocoder
) : GeocoderProvider {

    override fun provide(): Geocoder = geocoder

}