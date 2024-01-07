package com.klim.stock.symbol.usecase.impl

import android.graphics.Color
import com.klim.constants.qualifiers.DateFormatRequest
import com.klim.constants.qualifiers.TimezoneServer
import com.klim.stock.symbol.api.SymbolDetailsUseCase
import com.klim.stock.symbol.api.entity.RecommendedSymbolEntity
import com.klim.stock.symbol.api.entity.SymbolDetailsEntity
import com.klim.stock.symbol.api.entity.SymbolPriceSummaryEntity
import com.klim.stock.symbol.api.entity.OfficerEntity
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

    override suspend fun getDetails(params: SymbolDetailsUseCase.RequestParams): SymbolDetailsEntity? {
        val details = repositorySymbol.getDetails(params.symbol) ?: return null
        return SymbolDetailsEntity(
            symbol = details.symbol,
            name = details.name,
            sector = details.sector,
            industry = details.industry,
            ceo = findCEO(details.officers)?.name,
            employees = details.employees,
            address = details.address,
            phone = details.phone,
            description = details.description,
            officers = details.officers.map { OfficerEntity(it.name, it.title) },
            recommendedSymbols = details.recommendedSymbols.map { RecommendedSymbolEntity(it.symbol, getRandomColor()) },
            currentPrice = details.currentPrice,
            marketChange = details.marketChange,
            marketChangePercent = details.marketChangePercent,
        )
    }

    private fun findCEO(officers: List<OfficerEntity>): OfficerEntity? {
        return officers.firstOrNull { it.title.contains("CEO", true) }
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

    private fun getRandomColor(): Int {
        val darkerColor = 50
        val brighterColor = 200
        return Color.argb(
            255,
            darkerColor + Random.nextInt(brighterColor - darkerColor),
            darkerColor + Random.nextInt(brighterColor - darkerColor),
            darkerColor + Random.nextInt(brighterColor - darkerColor)
        )
    }

}