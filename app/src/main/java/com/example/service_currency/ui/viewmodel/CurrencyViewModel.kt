package com.example.service_currency.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.service_currency.data.model.Currency
import com.example.service_currency.data.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.util.*

class CurrencyViewModel: ViewModel() {

    private val repository = CurrencyRepository()

    val mutableListCurrency = MutableLiveData<List<List<Currency>>>(null)

    fun getTodayTomorrowCurrency(date: String){
        viewModelScope.launch {
            val listFirstCurrency = repository.getTodayCurrency(date)
            val listSecondCurrency = repository.getTomorrowCurrency("2022-03-27")
            val absoluteList = listOf(listFirstCurrency,listSecondCurrency)
            mutableListCurrency.postValue(absoluteList)
        }
    }
}