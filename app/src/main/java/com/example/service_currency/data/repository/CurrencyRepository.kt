package com.example.service_currency.data.repository

import android.content.Context
import com.example.service_currency.data.api.NetWorkService
import com.example.service_currency.data.db.CurrencyEntity
import com.example.service_currency.data.db.RoomDb
import com.example.service_currency.utils.ServiceCurrencyApplication
import java.util.*

object CurrencyRepository {

    private val apiCurrency = NetWorkService.retrofitService()

    private val roomDb: RoomDb = RoomDb.getDatabase()

    suspend fun getTodayCurrency(date: String) = apiCurrency.getTodayCurrency(date)

    suspend fun getTomorrowOrYesterdayCurrency(date: String) = apiCurrency.getTomorrowCurrency(date)

    suspend fun getCurrencyEntities() = roomDb.currencyDao().getCurrencies()
}