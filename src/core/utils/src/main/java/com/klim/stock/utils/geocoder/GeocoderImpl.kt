package com.klim.stock.utils.geocoder

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.klim.stock.utils.geocoder.api.Address
import com.klim.stock.utils.geocoder.api.Geocoder
import java.util.Locale
import android.location.Geocoder as GeocoderNative

class GeocoderImpl
constructor(
    context: Context,
    locale: Locale
) : Geocoder {
    private var geocoder: GeocoderNative = GeocoderNative(context, locale)

    override fun getFromLocationName(locationName: String, maxResults: Int): List<Address> {
        return geocoder.getFromLocationName(locationName, maxResults)
            ?.map {
                Address(
                    LatLng(
                        it.latitude, it.longitude
                    )
                )
            }
            ?: emptyList()
    }
}