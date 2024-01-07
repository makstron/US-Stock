package com.klim.stock.symbol.ui.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.klim.stock.analytics.analytics.Analytics
import com.klim.stock.analytics.crashliytics.Crashlytics
import com.klim.stock.analytics.crashliytics.FirebaseCrashKeys
import com.klim.stock.analytics.analytics.FirebaseLogKeys
import com.klim.stock.symbol.api.entity.SymbolDetailsEntity
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.chart.smoothie.ChartDataItem
import com.klim.stock.history.usecase.api.HistoryUseCase
import com.klim.stock.symbol.api.SymbolDetailsUseCase
import com.klim.stock.symbol.ui.R
import com.klim.stock.resources.R as Res
import com.klim.stock.utils.geocoder.api.Address
import com.klim.stock.utils.geocoder.api.Geocoder
import com.klim.stock.symbol.ui.presentation.entity.DetailsResultView
import com.klim.stock.symbol.ui.presentation.entity.OfficerEntityView
import com.klim.stock.symbol.ui.presentation.entity.PriceEntityView
import com.klim.stock.symbol.ui.presentation.entity.RecommendedEntityView
import com.klim.stock.utils.phonenumber.api.PhoneNumberUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject


class SymbolDetailsViewModel
@Inject
constructor(
    application: Application,
    private val symbolDetailsUseCase: SymbolDetailsUseCase,
    private val historyUseCase: HistoryUseCase,
    private val geocoder: Geocoder,
    private val phoneNumberUtil: PhoneNumberUtils,
    private val analytics: Analytics,
    private val crashlytics: Crashlytics,
) : AndroidViewModel(application) {

    private val employeesFormatter = DecimalFormat("#,###")

    lateinit var currentSymbol: String

    //
    private val _detailsResults = MutableLiveData<DetailsResultView>()
    val detailsResults: LiveData<DetailsResultView> = _detailsResults

    private val _geocodedAddress = MutableLiveData<LatLng?>()
    val geocodedAddress: LiveData<LatLng?> = _geocodedAddress

    private val _price = MutableLiveData<PriceEntityView>()
    val price: LiveData<PriceEntityView> = _price

    private val _history = MutableLiveData<List<ChartDataItem>?>()
    val history: LiveData<List<ChartDataItem>?> = _history

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isExistsResult = MutableLiveData<Boolean>(true)
    val isExistsResult: LiveData<Boolean> = _isExistsResult

    fun loadArguments(args: Bundle?) {
        args?.let { args ->
            currentSymbol = args.getString(SymbolDetailsFragment.SYMBOL) ?: ""
        }

        //logs
        crashlytics.setCustomKey(FirebaseCrashKeys.SYMBOL_DETAILS, currentSymbol)

        val bundle = Bundle()
        bundle.putString(FirebaseLogKeys.PARAM_SYMBOL, currentSymbol)
        analytics.logEvent(FirebaseLogKeys.ACTION_OPEN_SYMBOL_DETAILS, bundle)
    }

    fun loadDetails() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.Main) {

            val jobDetails = launch(Dispatchers.Main) {
                val results = symbolDetailsUseCase.getDetails(SymbolDetailsUseCase.RequestParams(currentSymbol))
                results?.let {
                    val preparedResult = prepareDetailsResult(results)
                    _detailsResults.postValue(preparedResult)
                    _geocodedAddress.postValue(geocodeAddress(results.address))
                    _price.postValue(preparePriceResult(results))
                }
                _isExistsResult.postValue(results != null)
            }

            val jobHistory = launch(Dispatchers.Main) {
                val lastMonthsHistory =
                    historyUseCase.getSymbolPricesHistory(
                        HistoryUseCase.RequestParams(
                            currentSymbol,
                            HistoryUseCase.RequestParams.Range.ONE_MONTH,
                            HistoryUseCase.RequestParams.TimeInterval.ONE_DAY
                        )
                    )
                if (lastMonthsHistory != null) {
                    _history.postValue(
                        lastMonthsHistory
                            .map {
                                ChartDataItem(it.time * 1000, it.priceClose.toFloat())
                            })
                } else {
                    _history.postValue(null)
                }
            }

            jobDetails.join()
            jobHistory.join()

            _isLoading.postValue(false)
        }
    }

    private suspend fun geocodeAddress(address: String): LatLng? {
        var latLng: LatLng? = null
        withContext(Dispatchers.IO) {
            val addresses = ArrayList<Address>()
            try {
                addresses.addAll(geocoder.getFromLocationName(address, 1))
            } catch (e: Exception) {
                e.printStackTrace()
                crashlytics.recordException(e)
            }
            if (addresses.isNotEmpty()) {
                latLng = addresses.first().location
            }
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
        val officers = results.officers.map { OfficerEntityView(it.name, it.title) }
        val recommendedSymbols = results.recommendedSymbols.map { RecommendedEntityView(it.symbol, it.color) }

        return DetailsResultView(
            symbol = results.symbol,
            name = results.name,
            sector = results.sector,
            industry = results.industry,
            ceo = results.ceo,
            employees = employeesFormatter.format(results.employees),
            address = results.address,
            phone = numberFormat(results.phone),
            description = results.description,
            officers = officers,
            recommendedSymbols = recommendedSymbols,
        )
    }

    private fun numberFormat(number: String): String {
        return try {
            phoneNumberUtil.format(number)
        } catch (e: Exception) {
            number
        }
    }

    private fun preparePriceResult(result: SymbolDetailsEntity?): PriceEntityView {
        var priceCurrent = getApplication<Application>().getString(R.string.data_not_loaded)
        var priceDifferentFormat = (getApplication() as ApplicationContextProvider).getAppContext().getString(R.string.data_not_loaded)
        var priceDifferentPercentFormat = ""
        var color: Int = Color.GRAY
        var arrow: Int = 0

        if (result != null) {

            priceCurrent = "$${result.currentPrice}"

            when {
                result.marketChange > 0 -> {
                    color = ContextCompat.getColor(getApplication(), Res.color.price_rise)
                    arrow = R.drawable.ic_arrow_up
                }

                result.marketChange < 0 -> {
                    color = ContextCompat.getColor(getApplication(), Res.color.price_fall)
                    arrow = R.drawable.ic_arrow_down
                }

                else -> {
                    color = Color.GRAY
                    arrow = R.drawable.ic_arrow_empty
                }
            }

            val priceDifferent = roundUpMoney(result.marketChange)
            val priceDifferentPercent = roundUpMoney(result.marketChangePercent)

            priceDifferentFormat = if (priceDifferent > 0) "+${priceDifferent}" else "$priceDifferent"
            priceDifferentPercentFormat = "$priceDifferentPercent%".replace("-", "")
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
