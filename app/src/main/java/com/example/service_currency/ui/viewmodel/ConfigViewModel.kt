package com.example.service_currency.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.service_currency.data.db.CurrencyEntity
import com.example.service_currency.data.db.RoomDb
import com.example.service_currency.data.repository.CurrencyRepository
import com.example.service_currency.data.converter.DateConvert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import java.time.LocalDate

class ConfigViewModel : ViewModel() {
    private val repository = CurrencyRepository

    //  val mutableListConfigCurrency = MutableLiveData<List<CurrencyEntity>>(null)

    var listStateCurrency = mutableStateListOf<CurrencyEntity>()

    fun getCurrency(date: LocalDate, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
           /* repeat(100) {
                listStateCurrency.add(it.toString())
            }*/
            val db: RoomDb = RoomDb.getDatabase()
            if (db.currencyDao().getCurrencies().isEmpty()) {
               // db.clearAllTables()
                val listCurrency = repository.getTodayCurrency(DateConvert.formatForRequest(date))
                val listCurrencyEntity = listCurrency.map {
                    CurrencyEntity(
                        it.curAbbreviation,
                        it.curScale,
                        it.curName,
                        listCurrency.indexOf(it)
                    )
                }
                listStateCurrency.addAll(listCurrencyEntity)
                db.currencyDao().insertFirstTimeAllCurrencies(listCurrencyEntity)

            } else {
               //db.clearAllTables()
               listStateCurrency.addAll(db.currencyDao().getCurrencies())
            }
        }

        /*   var list  = MutableLiveData<List<CurrencyEntity>>()

           viewModelScope.launch(Dispatchers.IO){
               val db: RoomDb = RoomDb.getDatabase(context)
               if(db.currencyDao().getCurrencies().isEmpty()){
                  // db.clearAllTables()
                   val listCurrency = repository.getTodayCurrency(date)
                   val listCurrencyEntity = listCurrency.map {
                       CurrencyEntity(it.curAbbreviation, it.curScale, it.curName, listCurrency.indexOf(it))
                   }
                   mutableListConfigCurrency.postValue(listCurrencyEntity)
                   db.currencyDao().insertFirstTimeAllCurrencies(listCurrencyEntity)
                   list =  mutableListConfigCurrency
               }
               else{
                   val currencies = db.currencyDao().getCurrencies()
                   mutableListConfigCurrency.postValue(currencies)
                   list = mutableListConfigCurrency
               }
           }
           return list.value!!*/
    }


    fun updateCurrenciesInDb(context: Context, list: List<CurrencyEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            val db: RoomDb = RoomDb.getDatabase()
            if (/*mutableListConfigCurrency.value?.isNotEmpty()*/ list.isNotEmpty()) {
                //val entities = mutableListConfigCurrency.value
                db.currencyDao().updateListCurrency(/*entities!!*/list)
            }
        }
    }


}