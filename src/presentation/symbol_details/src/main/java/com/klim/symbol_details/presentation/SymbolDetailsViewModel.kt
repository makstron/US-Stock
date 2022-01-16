package com.klim.symbol_details.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.klim.analytics.AnalyticsI
import com.klim.analytics.firebase.FirebaseLogKeys
import com.klim.coreUi.domain.entity.SymbolDetailsEntity
import com.klim.coreUi.domain.entity.SymbolPriceSummaryEntity
import com.klim.dep_in.ApplicationContextProvider
import com.klim.smoothie_chart.ChartDataItem
import com.klim.symbol_details.R
import com.klim.symbol_details.presentation.entity.DetailsResultView
import com.klim.symbol_details.presentation.entity.PriceEntityView
import com.klim.symbol_details.presentation.entity.SimilarEntityView
import com.klim.symbol_details.presentation.entity.TagEntityView
import com.klim.symbol_details_usecase.SymbolDetailsUseCase
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
    private val geocoder: Geocoder,
//    private val firebaseCrashlytics: FirebaseCrashlytics,//todo modules
    private val analytics: AnalyticsI,
    private val phoneNumberUtil: PhoneNumberUtil,
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

    val data = ArrayList<ChartDataItem>()

    val isLoading = ObservableBoolean(false)
    val isExistsResult = ObservableBoolean(true)

    fun loadArguments(args: Bundle?) {
        args?.let { args ->
            currentSymbol = args.getString(SymbolDetailsFragment.SYMBOL) ?: ""
        }

        //logs
//        firebaseCrashlytics.setCustomKey(FirebaseCrashKeys.SYMBOL_DETAILS, currentSymbol) //todo modules

        val bundle = Bundle()
        bundle.putString(FirebaseLogKeys.PARAM_SYMBOL, currentSymbol)
        analytics.logEvent(FirebaseLogKeys.ACTION_OPEN_SYMBOL_DETAILS, bundle)
    }

    fun loadDetails() {
        isLoading.set(true)
        viewModelScope.launch(Dispatchers.Main) {

            val jobDetails = launch(Dispatchers.Main) {
                val results = symbolDetailsUseCase.getDetails(SymbolDetailsUseCase.Params(currentSymbol))
                results?.let {
                    val preparedResult = prepareDetailsResult(results)
                    _detailsResults.postValue(preparedResult)

                    _geocodedAddress.postValue(geocodeAddress(results.address))
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

    private suspend fun geocodeAddress(address: String): LatLng? {
        var latLng: LatLng? = null
        withContext(Dispatchers.IO) {
            val addresses = ArrayList<Address>()
            try {
                addresses.addAll(geocoder.getFromLocationName(address, 1))
            } catch (e: Exception) {
                e.printStackTrace()
//                firebaseCrashlytics.recordException(e)//todo modules
            }
            if (addresses.isNotEmpty()) {
                val addressLocation = addresses.first()
                latLng = LatLng(addressLocation.latitude, addressLocation.longitude)
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
            phone = numberFormat(results.phone),
            description = results.description,
            tags = tags,
            similar = similar,
        )
    }

    private fun numberFormat(number: String): String {
        return try {
            phoneNumberUtil.format(
                phoneNumberUtil.parse(number, "US"),
                PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL
            )
        } catch (e: Exception) {
            number
        }
    }

    private fun preparePriceResult(price: SymbolPriceSummaryEntity?): PriceEntityView {
        var priceCurrent = getApplication<Application>().getString(R.string.data_not_loaded)//todo modules
        var priceDifferentFormat = (getApplication() as ApplicationContextProvider).getAppContext().getString(R.string.data_not_loaded) //todo modules
        var priceDifferentPercentFormat = ""
        var color: Int = Color.GRAY
        var arrow: Int = 0

        if (price != null) {

            priceCurrent = "$${price.currentPrice}"

            if (price.valueFromLatest != null && price.percentFromLatest != null) {
                when {
                    price.valueFromLatest!! > 0 -> { //todo modules !!
                        color = Color.parseColor("#0ac269")
                        arrow = R.drawable.ic_arrow_up
                    }
                    price.valueFromLatest!! < 0 -> {//todo modules !!
                        color = Color.parseColor("#E51616")
                        arrow = R.drawable.ic_arrow_down
                    }
                    else -> {
                        color = Color.GRAY
                        arrow = R.drawable.ic_arrow_empty
                    }
                }

                val priceDifferent = roundUpMoney(price.valueFromLatest!!) //todo modules !!
                val priceDifferentPercent = roundUpMoney(price.percentFromLatest!!) //todo modules !!

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
