package com.example.service_currency.utils

import android.app.Application
import com.example.service_currency.data.db.RoomDb

class ServiceCurrencyApplication : Application() {
    init {
        RoomDb.context = this
    }
}