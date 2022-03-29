package com.example.service_currency.data.api

import com.example.service_currency.data.model.Currency
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface Api {

    @GET("rates?periodicity=0")
    suspend fun getTodayCurrency(@Query("ondate") todayDate: String): List<Currency>

    @GET("rates?periodicity=0")
    suspend fun getTomorrowCurrency(@Query("ondate") tomorrowDate: String): List<Currency>

    @GET("rates?periodicity=0")
    suspend fun getYesterdayCurrency(@Query("ondate") todayDate: Date): List<Currency>


}