package com.example.service_currency.data.repository

import com.example.service_currency.data.api.NetWorkService
import java.util.*

class CurrencyRepository {

    private val apiCurrency = NetWorkService.retrofitService()

    suspend fun getTodayCurrency(date: String) = apiCurrency.getTodayCurrency(date)

    suspend fun getTomorrowCurrency(date: String) = apiCurrency.getTomorrowCurrency(date)
}