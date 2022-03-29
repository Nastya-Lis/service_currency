package com.example.service_currency.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyEntity (
    @PrimaryKey
    val curId: String,
    @ColumnInfo(name = "scale")
    val scale: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "checked")
    val checked: Boolean = true
    )