package com.klim.stock.favorited.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.klim.chart.cosmoothie.ChartData
import com.klim.chart.cosmoothie.CoSmoothieChart
import com.klim.chart.entity.ChartDataItem
import com.klim.stock.favorited.ui.api.SymbolFavoritedViewModel
import com.klim.stock.resources.AppTheme
import com.klim.stock.resources.UiText
import kotlin.math.absoluteValue
import kotlin.random.Random
import com.klim.stock.resources.R as Res

data class ScreenState(
    val favoritedItems: List<FavoritedSymbol>,
    val orderDirectionEnums: List<OrderDirection>,
) {
    companion object {
        fun initial() = ScreenState(
            favoritedItems = emptyList(),
            orderDirectionEnums = emptyList(),
        )
    }
}

sealed class Intents {
    class OpenDetails(val symbol: String) : Intents()
    class ChangeOrderOption(val orderOption: OrderDirectionEnum) : Intents()
}

enum class Trend {
    UP, NONE, DOWN;
}

enum class OrderDirectionEnum {
    RECENT, ALPHABETICALLY;
}

data class OrderDirection(
    val orderDirection: OrderDirectionEnum,
    val label: UiText,
    val status: Boolean,
)

data class FavoritedSymbol(
    val symbol: String,
    val shortName: String,
    val price: String,
    val trend: Trend,
    val trendValue: String,
    val trendPercent: String,
    val chartData: ChartData,
)

//@OptIn(ExperimentalFoundationApi::class)
//private val threePagesPerViewport = object : PageSize {
//    override fun Density.calculateMainAxisPageSize(
//        availableSpace: Int,
//        pageSpacing: Int
//    ): Int {
//        return (availableSpace - 2 * pageSpacing) / 1
//    }
//}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritedScreen(viewModel: SymbolFavoritedViewModel) {
    println("@@@ FavoritedScreen recompose")

    val screenState: ScreenState by viewModel.screenState.observeAsState(ScreenState.initial())

    val listener: ((Intents) -> Unit) = { intent ->
        viewModel.sendIntent(intent)
    }
    var size by remember { mutableStateOf(2000) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                size = it.width
            },
    ) {
        val pagerState = rememberPagerState(
            initialPage = 1,
            initialPageOffsetFraction = 0f
        ) { screenState.favoritedItems.size }


        val fling = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(1)
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(Color.Gray.copy(alpha = 0.1f))
        ) {
            Text(
                text = "Favourite",
                style = AppTheme.styles.displayMedium,
                modifier = Modifier.padding(24.dp)
            )
            OrderDirectionLine(
                screenState.orderDirectionEnums, {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
//            pageSize = threePagesPerViewport,
                contentPadding = PaddingValues(horizontal = 60.dp),
                pageSpacing = 0.dp,
                modifier = Modifier.background(Color.Gray.copy(alpha = 0.0f)),
                flingBehavior = fling,
                beyondBoundsPageCount = 1,
            ) { pageIdx ->
                if (screenState.favoritedItems.isNotEmpty()) {
                    FavoritedItem(
                        symbol = screenState.favoritedItems[pageIdx],
                        clickListener = listener,
                        modifier = Modifier
                            .height(600.dp)
                            .graphicsLayer {
                                val pageOffset = (
                                        (pagerState.currentPage - pageIdx) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue
                                lerp(
                                    start = 0.85f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                ).also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }
                                alpha = lerp(
                                    start = 0.5f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            }

                    )
                }
            }
        }


    }
}

@Composable
fun OrderDirectionLine(
    list: List<OrderDirection>,
    changeDirectionListener: (OrderDirectionEnum) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
    ) {
        items(
            items = list,
            key = {
                it.orderDirection
            }
        ) { direction ->
            OrderButton(
                direction = direction,
                clickListener = { changeDirectionListener(direction.orderDirection) },
            )

        }
    }
}

@Composable
fun FavoritedItem(
    symbol: FavoritedSymbol,
    clickListener: (Intents) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .padding(5.dp)
            .clickable { clickListener(Intents.OpenDetails(symbol.symbol)) }
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(24.dp)),
        backgroundColor = Color.White //(0xFF8A6EBA),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = symbol.symbol,
                style = AppTheme.styles.displayMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .absolutePadding(
                        top = 16.dp,
                        left = 16.dp,
                        right = 16.dp,
                    ),
            )
            Text(
                text = symbol.shortName,
                modifier = Modifier
                    .fillMaxWidth()
                    .absolutePadding(
                        left = 16.dp,
                        right = 16.dp,
                    ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .absolutePadding(
                        left = 16.dp,
                        right = 16.dp,
                    ),
            ) {
                val color by remember {
                    mutableStateOf(
                        when (symbol.trend) {
                            //TODO: now colors from theme
                            Trend.UP -> Color.Green
                            Trend.NONE -> Color.Black
                            Trend.DOWN -> Color.Red
                        }
                    )
                }

                Text(
                    text = symbol.price,
                    style = AppTheme.styles.headLineLarge,
                )

                val arrowDirection = when (symbol.trend) {
                    Trend.UP -> painterResource(id = Res.drawable.ic_arrow_up)
                    Trend.NONE -> painterResource(id = Res.drawable.ic_arrow_empty)
                    Trend.DOWN -> painterResource(id = Res.drawable.ic_arrow_up)
                }
                Image(
                    painter = arrowDirection,
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight(),
                    colorFilter = ColorFilter.tint(color),
                )

                Column {
                    Text(
                        text = symbol.trendValue,
                        style = AppTheme.styles.labelSmall,
                        color = color,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                    )
                    Text(
                        text = "${symbol.trendPercent}%",
                        style = AppTheme.styles.labelSmall,
                        color = color,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            CoSmoothieChart(
                data = symbol.chartData,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            )
        }
    }
}

@Composable
fun OrderButton(
    direction: OrderDirection,
    clickListener: (OrderDirectionEnum) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = direction.label.asString(),
        color = colorResource(id = Res.color.gray_15),
        style = AppTheme.styles.bodyLarge,
        modifier = modifier
            .clickable { clickListener(direction.orderDirection) }
            .padding(end = 8.dp)
            .clip(
                shape = RoundedCornerShape(size = 16.dp)
            )
            .background(
                color = if (direction.status) colorResource(id = Res.color.brand_red) else Color.Gray
            )
            .padding(8.dp),
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewOrderButton() {
    AppTheme {
        OrderButton(
            direction = OrderDirection(
                orderDirection = OrderDirectionEnum.ALPHABETICALLY,
                label = UiText(Res.string.favourite_order_alphabetically),
                status = true,
            ),
            clickListener = {},
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFavoritedSymbol() {
    AppTheme {
        FavoritedItem(
            symbol = createTestFavoritedSymbol(),
            clickListener = {},
        )
    }
}

@Preview
@Composable
fun PreviewScreen() {
    AppTheme {
        val viewModel = object : SymbolFavoritedViewModel() {
            override val screenState: LiveData<ScreenState>
                get() = MutableLiveData<ScreenState>(
                    ScreenState(
                        favoritedItems = listOf<FavoritedSymbol>(
                            createTestFavoritedSymbol(
                                symbol = "NFLX",
                                shortName = "Netflix, Inc.",
                            ),
                            createTestFavoritedSymbol(
                                symbol = "TSLA",
                                shortName = "Tesla, Inc.",
                            ),
                            createTestFavoritedSymbol(
                                symbol = "F",
                                shortName = "Ford",
                            ),
                            createTestFavoritedSymbol(
                                symbol = "NN",
                                shortName = "No Name. Inc.",
                            ),
                        ),
                        orderDirectionEnums = listOf(
                            OrderDirection(
                                orderDirection = OrderDirectionEnum.ALPHABETICALLY,
                                label = UiText(Res.string.favourite_order_alphabetically),
                                status = true,
                            )
                        )
                    )
                )

            override fun load() {}

            override fun sendIntent(intent: Intents) {}

        }
        FavoritedScreen(viewModel)
    }
}


private fun createTestFavoritedSymbol(
    symbol: String = "NFLX",
    shortName: String = "Netflix, Inc."
): FavoritedSymbol = FavoritedSymbol(
    symbol = symbol,
    shortName = shortName,
    price = String.format("%.2f", Random.nextDouble(10.0, 100.0)),
    trend = Trend.entries[Random.nextInt(Trend.entries.size)],
    trendValue = "2.5",
    trendPercent = "1",
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
    ),
)