package com.example.service_currency.data.repository

import android.content.Context
import com.example.service_currency.data.api.NetWorkService
import com.example.service_currency.data.db.CurrencyEntity
import com.example.service_currency.data.db.RoomDb
import java.util.*

class CurrencyRepository() {

    private val apiCurrency = NetWorkService.retrofitService()
    //private val roomDb: RoomDb = RoomDb.getDatabase(context)

    suspend fun getTodayCurrency(date: String) = apiCurrency.getTodayCurrency(date)

    suspend fun getTomorrowOrYesterdayCurrency(date: String) = apiCurrency.getTomorrowCurrency(date)

    /*fun insertDbCurrencies(currencies: List<CurrencyEntity>) =
        roomDb.insertFirstTimeCurrencies(currencies)*/

}