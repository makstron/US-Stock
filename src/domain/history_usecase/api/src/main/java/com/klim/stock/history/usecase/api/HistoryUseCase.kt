package com.klim.stock.history.usecase.api

import com.klim.stock.history.usecase.api.entity.SymbolHistoryPriceEntity

interface HistoryUseCase {

    class RequestParams(val symbol: String, val range: Range, val timeInterval: TimeInterval) {

        enum class Range {
            ONE_DAY,
            FIVE_DAYS,
            ONE_MONTH,
            SIX_MONTHS,
            ONE_YEAR,
            FIVE_YEARS,
            ;
        }

        enum class TimeInterval {
            ONE_MINUTE,
            TWO_MINUTES,
            FIVE_MINUTES,
            FIFTEEN_MINUTES,
            THIRTY_MINUTES,
            SIXTY_MINUTES,
            NINETY_MINUTES,
            ONE_HOUR,
            ONE_DAY,
            FIVE_DAYS,
            ONE_WEEK,
            ONE_MONTH,
            THREE_MONTHS,
            ;
        }

    }

    suspend fun getSymbolPricesHistory(params: RequestParams): List<SymbolHistoryPriceEntity>?

}

