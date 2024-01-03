package com.klim.stock.utils.geocoder.api

import com.google.android.gms.maps.model.LatLng

interface Geocoder {
    fun getFromLocationName(locationName: String, maxResults: Int): List<Address>
}

class Address(
    val location: LatLng
)