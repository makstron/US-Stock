package com.klim.stock.symbol.usecase.impl

import android.graphics.Color
import com.klim.constants.qualifiers.DateFormatRequest
import com.klim.constants.qualifiers.TimezoneServer
import com.klim.stock.symbol.api.SymbolDetailsUseCase
import com.klim.stock.symbol.api.entity.RelatedStockEntity
import com.klim.stock.symbol.api.entity.SymbolDetailsEntity
import com.klim.stock.symbol.api.entity.SymbolPriceSummaryEntity
import com.klim.stock.symbol.api.entity.TagEntity
import com.klim.stock.symbol.repository.api.SymbolRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject
import kotlin.random.Random

class SymbolDetailsUseCaseImpl
@Inject constructor(
    private val repositorySymbol: SymbolRepository,
    @DateFormatRequest
    private val dayFormat: SimpleDateFormat,
    @TimezoneServer
    private val serverTimezone: TimeZone,
) : SymbolDetailsUseCase {

    private var similarColorRed = Color.parseColor("#E83E3E")
    private var similarColorGreen = Color.parseColor("#58D38C")
    private var lastSimilarColor: Int = similarColorRed

    override suspend fun getDetails(params: SymbolDetailsUseCase.RequestParams): SymbolDetailsEntity? {
        val details = repositorySymbol.getDetails(params.symbol) ?: return null
        resetInitialColors()
        return SymbolDetailsEntity(
            symbol = details.symbol,
            name = details.name,
            sector = details.sector,
            industry = details.industry,
            ceo = details.ceo,
            employees = details.employees,
            address = details.address,
            phone = details.phone,
            description = details.description,
            tags = details.tags.map { TagEntity(it.tag, getRandomColor()) },
            relatedStocks = details.relatedStocks.map { RelatedStockEntity(it.symbol, getNextTagColor()) },
            currentPrice = details.currentPrice,
            marketChange = details.marketChange,
            marketChangePercent = details.marketChangePercent,
        )
    }

    override suspend fun getPrice(params: SymbolDetailsUseCase.RequestParams): SymbolPriceSummaryEntity? {
        //TODO: now
//        val date = getLastAndPreviousAvailableDays()
//        val lastAvailableDay = date.first
//        val previousAvailableDay = date.second
//        val lastPrice: SymbolPriceEntity?
//        val previousPrice: SymbolPriceEntity?
//        coroutineScope {
//            val defLastPrice = async(Dispatchers.IO) {
//                repositoryStock.getPriceForDate(params.symbol, lastAvailableDay)
//            }
//            val defPreviousPrice = async(Dispatchers.IO) {
//                repositoryStock.getPriceForDate(params.symbol, previousAvailableDay)
//            }
//            lastPrice = defLastPrice.await()
//            previousPrice = defPreviousPrice.await()
//        }
//        if (lastPrice != null && previousPrice != null) {
//            return SymbolPriceSummaryEntity(
//                currentPrice = lastPrice.close,
//                valueFromLatest = lastPrice.close - previousPrice.close,
//                percentFromLatest = (lastPrice.close * 100f / previousPrice.close) - 100,
//            )
//        } else if (lastPrice != null) {
//            return SymbolPriceSummaryEntity(
//                currentPrice = lastPrice.close,
//                valueFromLatest = null,
//                percentFromLatest = null,
//            )
//        } else {
//            return null
//        }

        return null
    }

    private fun getLastAndPreviousAvailableDays(): Pair<String, String> {
        val cal = Calendar.getInstance(serverTimezone)
        cal.add(Calendar.DAY_OF_YEAR, -1)

        var previousAvailableDay: String = ""
        var lastAvailableDay: String = ""

        when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> {
                lastAvailableDay = dayFormat.format(cal.timeInMillis)
                cal.add(Calendar.DAY_OF_YEAR, -3)
                previousAvailableDay = dayFormat.format(cal.timeInMillis)
            }

            Calendar.SATURDAY -> {
                cal.add(Calendar.DAY_OF_YEAR, -1)
                lastAvailableDay = dayFormat.format(cal.timeInMillis)
                cal.add(Calendar.DAY_OF_YEAR, -1)
                previousAvailableDay = dayFormat.format(cal.timeInMillis)
            }

            Calendar.SUNDAY -> {
                cal.add(Calendar.DAY_OF_YEAR, -2)
                lastAvailableDay = dayFormat.format(cal.timeInMillis)
                cal.add(Calendar.DAY_OF_YEAR, -1)
                previousAvailableDay = dayFormat.format(cal.timeInMillis)
            }

            else -> {
                lastAvailableDay = dayFormat.format(cal.timeInMillis)
                cal.add(Calendar.DAY_OF_YEAR, -1)
                previousAvailableDay = dayFormat.format(cal.timeInMillis)
            }
        }
//        println("lastAvailableDay - $lastAvailableDay   previousAvailableDay - $previousAvailableDay")
        return Pair(lastAvailableDay, previousAvailableDay)
    }

    private fun getDayWithShift(shift: Int = 0): String {
        val cal = Calendar.getInstance(serverTimezone)
        cal.add(Calendar.DAY_OF_YEAR, shift)
        return dayFormat.format(cal.timeInMillis)
    }

    private fun resetInitialColors() {
        lastSimilarColor = Color.RED
    }

    //Sorry, I don't know where the designer took that colors and principles to generate them
    private fun getRandomColor(): Int {
        val darkerColor = 70
        val brighterColor = 180
        return Color.argb(
            255,
            darkerColor + Random.nextInt(brighterColor - darkerColor),
            darkerColor + Random.nextInt(brighterColor - darkerColor),
            darkerColor + Random.nextInt(brighterColor - darkerColor)
        )
    }

    //Sorry, I don't know where the designer took that colors and principles to generate them
    private fun getNextTagColor(): Int {
        lastSimilarColor =
            if (lastSimilarColor == similarColorRed) {
                similarColorGreen
            } else {
                similarColorRed
            }
        return lastSimilarColor
    }
}