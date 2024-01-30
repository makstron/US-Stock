package com.klim.stock.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.klim.stock.database.dao.FavouriteDAO
import com.klim.stock.database.entity.FavouriteEntity

@Database(entities = [FavouriteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavoritedDao(): FavouriteDAO


}
