package com.klim.stock.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favourite")
class FavouriteEntity(
    @PrimaryKey
    var symbol: String = "",

    var shortName: String? = null,
    var regularPrice: Float? = null,
    var regularMarketChange: Float? = null,
    var regularMarketChangePercent: Float? = null,

    @ColumnInfo(defaultValue = 0L.toString())
    var updatedTimestamp: Long = 0L,
)