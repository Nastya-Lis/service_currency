package com.example.service_currency.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.service_currency.data.model.Currency
import com.example.service_currency.data.repository.CurrencyRepository
import kotlinx.coroutines.launch

class ConfigViewModel : ViewModel() {

    private val repository = CurrencyRepository()

    val mutableListConfigCurrency = MutableLiveData<List<Currency>>(null)

    fun getCurrency(date: String){
        viewModelScope.launch {
            val listCurrency = repository.getTodayCurrency(date)
            mutableListConfigCurrency.postValue(listCurrency)
        }
    }

}