package com.example.service_currency.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.service_currency.data.db.CurrencyEntity
import com.example.service_currency.data.db.RoomDb
import com.example.service_currency.data.model.Currency
import com.example.service_currency.data.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfigViewModel : ViewModel() {


    private val repository = CurrencyRepository()

    val mutableListConfigCurrency = MutableLiveData<List<CurrencyEntity>>(null)

    fun getCurrency(date: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val db: RoomDb = RoomDb.getDatabase(context)
            if(db.currencyDao().getCurrencies().isEmpty()){
               // db.clearAllTables()
                val listCurrency = repository.getTodayCurrency(date)
                val listCurrencyEntity = listCurrency.map {
                    CurrencyEntity(it.curAbbreviation, it.curScale, it.curName, listCurrency.indexOf(it))
                }
                mutableListConfigCurrency.postValue(listCurrencyEntity)
                db.currencyDao().insertFirstTimeAllCurrencies(listCurrencyEntity)
            }
            else{
                val currencies = db.currencyDao().getCurrencies()
                mutableListConfigCurrency.postValue(currencies)
            }
        }
    }


    fun updateCurrenciesInDb(context: Context, list: List<CurrencyEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            val db: RoomDb = RoomDb.getDatabase(context)
            if (/*mutableListConfigCurrency.value?.isNotEmpty()*/ list.isNotEmpty()) {
                //val entities = mutableListConfigCurrency.value
                db.currencyDao().updateListCurrency(/*entities!!*/list)
            }
        }
    }


}