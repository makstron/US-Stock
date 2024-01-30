package com.klim.stock.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.klim.stock.database.entity.FavouriteEntity


@Dao
interface FavouriteDAO {

    @Query("SELECT * FROM favourite")
    fun getAll(): List<FavouriteEntity>

    @Query("SELECT * FROM favourite WHERE symbol LIKE :symbol")
    fun get(symbol: String): FavouriteEntity?

    @Query("SELECT * FROM favourite WHERE symbol IN (:symbols)")
    fun get(symbols: List<String>): List<FavouriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favourite: FavouriteEntity)

    @Delete
    fun delete(user: FavouriteEntity)

}
