package com.klim.us_stock.data.repository.symbol

import android.graphics.Color
import com.klim.us_stock.data.repository.symbol.data_source.SymbolDataSourceI
import com.klim.us_stock.domain.entity.RelatedStockEntity
import com.klim.us_stock.domain.entity.SearchResultEntity
import com.klim.us_stock.domain.entity.SymbolDetailsEntity
import com.klim.us_stock.domain.entity.TagEntity
import com.klim.us_stock.domain.repository.SymbolRepositoryI
import kotlin.random.Random

class SymbolRepository(private val remoteDataSource: SymbolDataSourceI) : SymbolRepositoryI {

    private var similarColorRed = Color.parseColor("#E83E3E")
    private var similarColorGreen = Color.parseColor("#58D38C")
    private var lastSimilarColor: Int = similarColorRed

    override suspend fun search(query: String): List<SearchResultEntity> {
        return remoteDataSource.search(query).map {
            it.map()
        }
    }

    override suspend fun getDetails(symbol: String): SymbolDetailsEntity? {
        val details = remoteDataSource.getDetails(symbol)
        details?.let {
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
            )
        } ?: run {
            return null
        }
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
        if (lastSimilarColor == similarColorRed) {
            lastSimilarColor = similarColorGreen
        } else {
            lastSimilarColor = similarColorRed
        }
        return lastSimilarColor
    }
}