package com.klim.stock.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorited")
class FavoritedEntity(
    @PrimaryKey
    var symbol: String = ""
)