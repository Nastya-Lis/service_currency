package com.example.service_currency.data.converter

import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DateConvert {

    companion object{
        fun formatForView(date: LocalDate): String{
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            return date.format(formatter)
        }

        fun formatForRequest(date: LocalDate): String{
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return date.format(formatter)
        }
    }

}