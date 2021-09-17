package com.klim.us_stock.ui.windows.symbol_details

import android.app.Application
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.klim.us_stock.domain.entity.SymbolDetailsEntity
import com.klim.us_stock.domain.repository.StockRepositoryI
import com.klim.us_stock.domain.repository.SymbolRepositoryI
import com.klim.us_stock.ui.windows.symbol_details.entity.DetailsResultView
import com.klim.us_stock.ui.windows.symbol_details.entity.PriceEntityView
import com.klim.us_stock.ui.windows.symbol_details.entity.SimilarEntityView
import com.klim.us_stock.ui.windows.symbol_details.entity.TagEntityView
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import android.annotation.SuppressLint
import com.klim.smoothie_chart.ChartDataItem
import com.klim.us_stock.R
import com.klim.us_stock.domain.entity.SymbolPriceEntity
import com.klim.us_stock.domain.repository.HistoryRepositoryI
import java.lang.Exception
import java.lang.RuntimeException
import javax.inject.Inject


class SymbolDetailsViewModel
@Inject
constructor(
    application: Application,
    private val repository: SymbolRepositoryI,
    private val repositoryStock: StockRepositoryI,
    private val repositoryHistory: HistoryRepositoryI,
) : AndroidViewModel(application) {

    private val geocoder = Geocoder(application, Locale.getDefault())
    private val employeesFormatter = DecimalFormat("#,###")

    lateinit var currentSymbol: String

    //
    private val _detailsResults = MutableLiveData<DetailsResultView>()
    val detailsResults: LiveData<DetailsResultView> = _detailsResults

    private val _geocodedAddress = MutableLiveData<LatLng>()
    val geocodedAddress: LiveData<LatLng> = _geocodedAddress

    private val _price = MutableLiveData<PriceEntityView>()
    val price: LiveData<PriceEntityView> = _price

    private val _history = MutableLiveData<List<ChartDataItem>?>()
    val history: LiveData<List<ChartDataItem>?> = _history

    val data = ArrayList<ChartDataItem>()

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

            val jobDetails = launch(Dispatchers.Main) {
                val results = getDetails(currentSymbol)
                results?.let {
                    val preparedResult = prepareDetailsResult(results)
                    _detailsResults.postValue(preparedResult)

                    val addressGeocoded = geocodeAddress(results.address)
                    _geocodedAddress.postValue(addressGeocoded)
                }
                isExistsResult.set(results != null)
            }

            val jobPrice = launch(Dispatchers.Main) {
                val defLastPrice = async(Dispatchers.IO) {
                    repositoryStock.getLastPrice(currentSymbol)
                }
                val defPreviousPrice = async(Dispatchers.IO) {
                    repositoryStock.getPreviousPrice(currentSymbol)
                }
                val lastPrice = defLastPrice.await()
                val previousPrice = defPreviousPrice.await()

                _price.postValue(
                    preparePriceResult(lastPrice, previousPrice)
                )
            }

            val jobHistory = launch(Dispatchers.Main) {
                val lastMonthsHistory = repositoryHistory.getLastMonthPrices(currentSymbol)
                if (lastMonthsHistory != null) {
                    _history.postValue(lastMonthsHistory?.map {
                        ChartDataItem(it.time, it.priceClose)
                    })
                } else {
                    _history.postValue(null)
                }
            }

            jobDetails.join()
            jobPrice.join()
            jobHistory.join()

            isLoading.set(false)
        }
    }

    @SuppressLint("DefaultLocale")
    fun roundUpMoney(value: Float): Float {
        return try {
            val bd = BigDecimal("" + value)
            val rounded = bd.setScale(2, RoundingMode.HALF_UP)
            rounded.toFloat()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw RuntimeException(ex)
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

    private fun preparePriceResult(lastPrice: SymbolPriceEntity?, previousPrice: SymbolPriceEntity?): PriceEntityView? {
        if (lastPrice != null && previousPrice != null) {
            val color: Int
            val arrow: Int
            when {
                lastPrice.close > previousPrice.close -> {
                    color = Color.parseColor("#0ac269")
                    arrow = R.drawable.ic_arrow_up
                }
                lastPrice.close < previousPrice.close -> {
                    color = Color.parseColor("#E51616")
                    arrow = R.drawable.ic_arrow_down
                }
                else -> {
                    color = Color.GRAY
                    arrow = R.drawable.ic_arrow_empty
                }
            }

            val priceDifferent = roundUpMoney(lastPrice.close - previousPrice.close)
            val priceDifferentPercent = roundUpMoney((lastPrice.close * 100f / previousPrice.close) - 100)

            val priceDifferentS = if (priceDifferent > 0) "+${priceDifferent}" else "$priceDifferent"
            val priceDifferentPercentS = "$priceDifferentPercent".replace("-", "")

            return PriceEntityView(
                currentPrice = "$${lastPrice.close}",
                color = color,
                arrow = arrow,
                priceDifferent = priceDifferentS,
                priceDifferentPercent = priceDifferentPercentS,
            )
        } else {
            return null
        }
    }

}
