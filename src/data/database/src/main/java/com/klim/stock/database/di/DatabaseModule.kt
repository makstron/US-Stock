package com.klim.stock.database.di

import android.content.Context
import androidx.room.Room
import com.klim.stock.database.AppDatabase
import com.klim.stock.database.dao.FavoritedDAO
import dagger.Module
import dagger.Provides


@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "stock_db"
        ) //.allowMainThreadQueries() - дозволяє запити в головному потоці
            //.fallbackToDestructiveMigration() - якщо будуть зміни в структурі БД а міграції не буде то перестворить БД
            .build()
        return db
    }

    @Provides
    fun provideFavoritedDTO(db: AppDatabase): FavoritedDAO {
        return db.getFavoritedDao()
    }

}