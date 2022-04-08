package com.example.service_currency.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity (
    @PrimaryKey
    var curId: String,
    @ColumnInfo(name = "scale")
    var scale: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "position")
    var position: Int,
    @ColumnInfo(name = "checked")
    var checked: Boolean = true
    )