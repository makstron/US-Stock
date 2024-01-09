package com.klim.stock.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.klim.stock.database.entity.FavoritedEntity


@Dao
interface FavoritedDAO {

    @Query("SELECT * FROM favorited")
    fun getAll(): List<FavoritedEntity>?

    @Query("SELECT * FROM favorited WHERE symbol LIKE :symbol")
    fun get(symbol: String): FavoritedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorit: FavoritedEntity)

    @Delete
    fun delete(user: FavoritedEntity)

}
