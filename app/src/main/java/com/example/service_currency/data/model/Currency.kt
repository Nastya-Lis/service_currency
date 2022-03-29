package com.example.service_currency.data.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class Currency(
    @SerializedName("Cur_ID")
    val curId: Int,
    @SerializedName("Cur_Abbreviation")
    val curAbbreviation: String,
    @SerializedName("Date")
    val data: String,
    @SerializedName("Cur_Scale")
    val curScale: Int,
    @SerializedName("Cur_Name")
    val curName: String,
    @SerializedName("Cur_OfficialRate")
    val curOfficialRate: Double
    )