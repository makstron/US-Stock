package com.klim.stock.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "charts_data",
    indices = [Index(value = ["timestamp"])]
)
class ChartsDataEntity(
    @PrimaryKey
    var symbol: String = "",
    var timestamp: Long,
    var close: Double,
    var low: Double,
    var open: Double,
    var high: Double,
    var volume: Int,
)