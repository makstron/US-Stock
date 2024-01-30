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
import com.klim.chart.entity.ChartDataItem
import com.klim.stock.analytics.analytics.Analytics
import com.klim.stock.analytics.crashliytics.Crashlytics
import com.klim.stock.analytics.crashliytics.FirebaseCrashKeys
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.favorited.usecase.api.FavoritedUseCase
import com.klim.stock.chart.usecase.api.ChartUseCase
import com.klim.stock.symbol.api.SymbolDetailsUseCase
import com.klim.stock.symbol.api.entity.SymbolDetailsEntity
import com.klim.stock.symbol.ui.R
import com.klim.stock.symbol.ui.presentation.entity.DetailsResultView
import com.klim.stock.symbol.ui.presentation.entity.OfficerEntityView
import com.klim.stock.symbol.ui.presentation.entity.PriceEntityView
import com.klim.stock.symbol.ui.presentation.entity.RecommendedEntityView
import com.klim.stock.utils.coroutines.CoroutineDispatchers
import com.klim.stock.utils.geocoder.api.Address
import com.klim.stock.utils.geocoder.api.Geocoder
import com.klim.stock.utils.phonenumber.api.PhoneNumberUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject
import com.klim.stock.resources.R as Res


class SymbolDetailsViewModel
@Inject
constructor(
    application: Application,
    private val dispatchers: CoroutineDispatchers,
    private val chartUseCase: ChartUseCase,
    private val geocoder: Geocoder,
    private val phoneNumberUtil: PhoneNumberUtils,
    private val analytics: Analytics,
    private val crashlytics: Crashlytics,
    private val symbolDetailsUseCase: SymbolDetailsUseCase,
    private val favoritedUseCase: FavoritedUseCase,
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

    private val _chart = MutableLiveData<List<ChartDataItem>?>()
    val chart: LiveData<List<ChartDataItem>?> = _chart

    private val _favorited = MutableLiveData<Boolean>(false)
    val favorited: LiveData<Boolean> = _favorited

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

        analytics.logEventOpenSymbol(currentSymbol)
    }

    fun loadDetails() {
        _isLoading.postValue(true)
        viewModelScope.launch(dispatchers.Main) {

            val jobDetails = launch(dispatchers.IO) {
                val results = symbolDetailsUseCase.getDetails(SymbolDetailsUseCase.RequestParams(currentSymbol))
                results?.let {
                    val preparedResult = prepareDetailsResult(results)
                    _detailsResults.postValue(preparedResult)
                    _geocodedAddress.postValue(geocodeAddress(results.address))
                    _price.postValue(preparePriceResult(results))
                }
                _isExistsResult.postValue(results != null)
            }

            val jobChart = launch(dispatchers.IO) {
                val lastMonthsChartData = chartUseCase.getSymbolChartData(
                    ChartUseCase.RequestParams(
                        currentSymbol,
                        ChartUseCase.RequestParams.Range.ONE_MONTH,
                        ChartUseCase.RequestParams.TimeInterval.ONE_DAY
                    )
                )
                if (lastMonthsChartData != null) {
                    _chart.postValue(
                        lastMonthsChartData
                            .map {
                                ChartDataItem(it.time * 1000, it.priceClose.toFloat())
                            })
                } else {
                    _chart.postValue(null)
                }
            }

            val jobFavorited = launch(dispatchers.IO) {
                val isFavorited = favoritedUseCase.checkIsFavorited(currentSymbol)
                _favorited.postValue(isFavorited)
            }

            jobDetails.join()
            jobChart.join()
            jobFavorited.join()

            _isLoading.postValue(false)
        }
    }

    fun setFavorited() {
        viewModelScope.launch(dispatchers.IO) {
            val newValue = !(favorited.value ?: false)
            favoritedUseCase.setFavorited(currentSymbol, newValue)
            _favorited.postValue(newValue)
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
                    arrow = Res.drawable.ic_arrow_up
                }

                result.marketChange < 0 -> {
                    color = ContextCompat.getColor(getApplication(), Res.color.price_fall)
                    arrow = Res.drawable.ic_arrow_down
                }

                else -> {
                    color = Color.GRAY
                    arrow = Res.drawable.ic_arrow_empty
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
