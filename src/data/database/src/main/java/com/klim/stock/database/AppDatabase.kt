package com.klim.stock.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.klim.stock.database.dao.FavoritedDAO
import com.klim.stock.database.entity.FavoritedEntity

@Database(entities = [FavoritedEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavoritedDao(): FavoritedDAO


}
