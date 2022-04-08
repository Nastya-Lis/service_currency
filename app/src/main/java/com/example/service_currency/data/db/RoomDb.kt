package com.example.service_currency.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CurrencyEntity::class], version = 2)
abstract class RoomDb: RoomDatabase() {

    abstract fun currencyDao(): Dao

    companion object{
        private var INSTANCE: RoomDb? = null
        fun getDatabase(context:Context): RoomDb {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,RoomDb::class.java, "currencies")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}