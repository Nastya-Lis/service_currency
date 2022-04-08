package com.example.service_currency.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Query("SELECT * FROM currencies")
    suspend fun getCurrencies(): List<CurrencyEntity>

    @Insert
    suspend fun insertFirstTimeAllCurrencies(currencies: List<CurrencyEntity>)

    @Update
    suspend fun updateListCurrency(currencies: List<CurrencyEntity>)

    @Update
    suspend fun updateCurrency(currency: CurrencyEntity)
}