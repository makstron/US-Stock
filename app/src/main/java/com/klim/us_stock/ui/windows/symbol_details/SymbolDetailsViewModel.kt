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
import com.klim.us_stock.App
import com.klim.us_stock.R
import com.klim.us_stock.domain.entity.SymbolPriceEntity
import com.klim.us_stock.domain.entity.SymbolPriceSummaryEntity
import com.klim.us_stock.domain.repository.HistoryRepositoryI
import com.klim.us_stock.domain.usecase.SymbolDetailsUseCase
import java.lang.Exception
import java.lang.RuntimeException
import javax.inject.Inject


class SymbolDetailsViewModel
@Inject
constructor(
    application: Application,
    private val symbolDetailsUseCase: SymbolDetailsUseCase,
    private val geocoder: Geocoder,
) : AndroidViewModel(application) {

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
                val results = symbolDetailsUseCase.getDetails(SymbolDetailsUseCase.Params(currentSymbol))
                results?.let {
                    val preparedResult = prepareDetailsResult(results)
                    _detailsResults.postValue(preparedResult)

                    val addressGeocoded = geocodeAddress(results.address)
                    _geocodedAddress.postValue(addressGeocoded)
                }
                isExistsResult.set(results != null)
            }
//
            val jobPrice = launch(Dispatchers.Main) {
                val results = symbolDetailsUseCase.getPrice(SymbolDetailsUseCase.Params(currentSymbol))

                _price.postValue(
                    preparePriceResult(results)
                )
            }

            val jobHistory = launch(Dispatchers.Main) {
                val lastMonthsHistory = symbolDetailsUseCase.getLastMonthPrice(SymbolDetailsUseCase.Params(currentSymbol))
                if (lastMonthsHistory != null) {
                    _history.postValue(lastMonthsHistory.map {
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

    private suspend fun geocodeAddress(address: String): LatLng {
        val latLng: LatLng
        withContext(Dispatchers.IO) {
            val addressLocation = geocoder.getFromLocationName(address, 1).first()
            latLng = LatLng(addressLocation.latitude, addressLocation.longitude)
        }
        return latLng
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

    private fun preparePriceResult(price: SymbolPriceSummaryEntity?): PriceEntityView {
        var priceCurrent = (getApplication() as App).getString(R.string.data_not_loaded)
        var priceDifferentFormat = (getApplication() as App).getString(R.string.data_not_loaded)
        var priceDifferentPercentFormat = ""
        var color: Int = Color.GRAY
        var arrow: Int = 0

        if (price != null) {

            priceCurrent = "$${price.currentPrice}"

            if (price.valueFromLatest != null && price.percentFromLatest != null) {
                when {
                    price.valueFromLatest > 0 -> {
                        color = Color.parseColor("#0ac269")
                        arrow = R.drawable.ic_arrow_up
                    }
                    price.valueFromLatest < 0 -> {
                        color = Color.parseColor("#E51616")
                        arrow = R.drawable.ic_arrow_down
                    }
                    else -> {
                        color = Color.GRAY
                        arrow = R.drawable.ic_arrow_empty
                    }
                }

                val priceDifferent = roundUpMoney(price.valueFromLatest)
                val priceDifferentPercent = roundUpMoney(price.percentFromLatest)

                priceDifferentFormat = if (priceDifferent > 0) "+${priceDifferent}" else "$priceDifferent"
                priceDifferentPercentFormat = "$priceDifferentPercent".replace("-", "")
            }
        }

        return PriceEntityView(
            currentPrice = priceCurrent,
            color = color,
            arrow = arrow,
            priceDifferent = priceDifferentFormat,
            priceDifferentPercent = priceDifferentPercentFormat,
        )
    }

}
