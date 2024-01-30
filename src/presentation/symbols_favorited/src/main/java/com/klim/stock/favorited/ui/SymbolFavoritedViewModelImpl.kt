package com.klim.stock.favorited.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klim.chart.cosmoothie.ChartData
import com.klim.chart.entity.ChartDataItem
import com.klim.stock.favorited.ui.api.NavigationTarget
import com.klim.stock.favorited.ui.api.SymbolFavoritedViewModel
import com.klim.stock.favorited.usecase.api.FavoritedUseCase
import com.klim.stock.resources.R
import com.klim.stock.resources.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class SymbolFavoritedViewModelImpl
@Inject constructor(
    private val favoritedUseCase: FavoritedUseCase,
) : SymbolFavoritedViewModel() {

    private val _screenState = MutableLiveData<ScreenState>(ScreenState.initial())
    override val screenState: LiveData<ScreenState> = _screenState

    private val _navigation = MutableLiveData<NavigationTarget>()
    override val navigation: LiveData<NavigationTarget> = _navigation

    init {
        load()
    }

    override fun load() {
        viewModelScope.launch(Dispatchers.IO) {

            val storedFavoritedItems = favoritedUseCase.getFavoritedPreviewList()

            val favoritedItems = storedFavoritedItems?.map {
                FavoritedSymbol(
                    symbol = it.symbol,
                    shortName = it.shortName,
                    price = formatValue(it.regularPrice),
                    trend = getTrendFromMarketChange(it.regularMarketChange),
                    trendValue = formatValue(it.regularMarketChange),
                    trendPercent = formatValue(it.regularMarketChangePercent),
                    //TODO: chartData
                    chartData = ChartData(
                        chartColor = Color(0xEA4335),
                        chartGradientColorFrom = Color(0xEA4335).copy(alpha = 0.5f),
                        chartGradientColorTo = Color(0xEA4335).copy(alpha = 0.05f),
                        data = listOf(
                            ChartDataItem(1631246400000, 736.27f),
                            ChartDataItem(1631505600000, 543f),
                            ChartDataItem(1631592000000, 444.49f),
                            ChartDataItem(1631678400000, 550.83f),
                            ChartDataItem(1631764800000, 656.99f),
                            ChartDataItem(1631851200000, 756.59f),
                            ChartDataItem(1631937600000, 735f),
                            ChartDataItem(1632024000000, 756f),
                            ChartDataItem(1632110400000, 956.5f),
                            ChartDataItem(1632196800000, 807f),
                            ChartDataItem(1632283200000, 757.5f),
                            ChartDataItem(1632369600000, 757f),
                            ChartDataItem(1632456000000, 855f),
                            ChartDataItem(1632542400000, 750f),
                            ChartDataItem(1632628800000, 760f),
                        )
                    )
                )
            }

            val orderDirections = OrderDirectionEnum.values().map {
                OrderDirection(
                    orderDirection = it,
                    label = UiText(
                        stringId = when (it) {
                            OrderDirectionEnum.RECENT -> R.string.favourite_order_recently
                            OrderDirectionEnum.ALPHABETICALLY -> R.string.favourite_order_alphabetically
                        }
                    ),
                    status = Random.nextBoolean(),
                )
            }


            //TODO: now add loading data

            _screenState.postValue(
                ScreenState(
                    favoritedItems = favoritedItems ?: emptyList(),
                    orderDirectionEnums = orderDirections,
                )
            )
        }
    }

    private fun getTrendFromMarketChange(marketChange: Float): Trend = when {
        marketChange > 0 -> Trend.UP
        marketChange < 0 -> Trend.DOWN
        else -> Trend.NONE
    }

    private fun formatValue(value: Float): String = String.format("%.2f", value)

    override fun sendIntent(intent: Intents) {
        when (intent) {
            is Intents.OpenDetails -> _navigation.postValue(NavigationTarget.SymbolDetails(intent.symbol))
            is Intents.ChangeOrderOption -> TODO()
        }
    }

}