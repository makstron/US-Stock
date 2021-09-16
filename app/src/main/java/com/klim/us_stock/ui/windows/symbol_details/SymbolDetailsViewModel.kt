package com.klim.us_stock.ui.windows.symbol_details

import android.app.Application
import android.location.Geocoder
import android.os.Bundle
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.klim.us_stock.data.repository.symbol.StockSymbolRepository
import com.klim.us_stock.data.repository.symbol.data_source.remote.StockSymbolRemoteDataSource
import com.klim.us_stock.data.retrofit.RetrofitProvider
import com.klim.us_stock.domain.entity.SymbolDetailsEntity
import com.klim.us_stock.domain.repository.StockSymbolRepositoryI
import com.klim.us_stock.ui.windows.symbol_details.entity.DetailsResultView
import com.klim.us_stock.ui.windows.symbol_details.entity.SimilarEntityView
import com.klim.us_stock.ui.windows.symbol_details.entity.TagEntityView
import kotlinx.coroutines.*
import java.text.DecimalFormat
import java.util.*


class SymbolDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val geocoder = Geocoder(application, Locale.getDefault())
    private val repository: StockSymbolRepositoryI = StockSymbolRepository(StockSymbolRemoteDataSource(RetrofitProvider.get().searchStockSymbolApi))

    private val employeesFormatter = DecimalFormat("#,###")

    lateinit var currentSymbol: String

    //
    private val _detailsResults = MutableLiveData<DetailsResultView>()
    val detailsResults: LiveData<DetailsResultView> = _detailsResults

    private val _geocodedAddress = MutableLiveData<LatLng>()
    val geocodedAddress: LiveData<LatLng> = _geocodedAddress

    val isLoading = ObservableBoolean(false)
    val isExistsResult = ObservableBoolean(true)

    fun loadArguments(args: Bundle?) {
        args?.let { args ->
            currentSymbol = args.getString(SymbolDetailsFragment.SYMBOL) ?: ""
        }
    }

    fun loadDetails() {
        isLoading.set(true)
        viewModelScope.launch(Dispatchers.Main) {
            val results = getDetails(currentSymbol)

            results?.let {
                val addressGeocoded = geocodeAddress(results.address)
                val preparedResult = prepareDetailsResult(results)
                _detailsResults.postValue(preparedResult)
                _geocodedAddress.postValue(addressGeocoded)
            }

            isExistsResult.set(results != null)
            isLoading.set(false)
        }
    }

    private suspend fun getDetails(symbol: String): SymbolDetailsEntity? {
        val results: SymbolDetailsEntity?
        withContext(Dispatchers.IO) {
            results = repository.getDetails(symbol)
        }
        return results
    }

    private suspend fun geocodeAddress(address: String): LatLng {
        var addressGeocoded: LatLng
        withContext(Dispatchers.IO) {
            val addressLocation = geocoder.getFromLocationName(address, 1).first()
            addressGeocoded = LatLng(addressLocation.latitude, addressLocation.longitude)
        }
        return addressGeocoded
    }

    private fun prepareDetailsResult(results: SymbolDetailsEntity): DetailsResultView {
        val tags = results.tags.map { TagEntityView(it.tag, it.color) }
        val similar = results.relatedStocks.map { SimilarEntityView(it.symbol, it.color) }

        return DetailsResultView(
            symbol = results.symbol,
            name = results.name,
            sector = results.sector,
            industry = results.industry,
            ceo = results.ceo,
            employees = employeesFormatter.format(results.employees),
            address = results.address,
            phone = results.phone,
            description = results.description,
            tags = tags,
            similar = similar,
        )
    }

}
