package com.klim.stock.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.klim.stock.database.entity.ChartsDataEntity


@Dao
interface ChartsDataDAO {

    @Query("SELECT * FROM charts_data WHERE symbol LIKE :symbol")
    fun getAllFor(symbol: String): List<ChartsDataEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favourite: ChartsDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(favouriteList: List<ChartsDataEntity>)

    @Delete
    fun delete(user: ChartsDataEntity)

}
