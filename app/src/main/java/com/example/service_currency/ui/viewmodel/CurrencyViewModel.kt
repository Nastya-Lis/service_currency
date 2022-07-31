package com.example.service_currency.ui.viewmodel

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.service_currency.data.converter.DateConvert
import com.example.service_currency.data.db.RoomDb
import com.example.service_currency.data.model.Currency
import com.example.service_currency.data.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class CurrencyViewModel: ViewModel() {

    private val repository = CurrencyRepository

    val mutableListCurrency = MutableLiveData<List<List<Currency>>>(null)
    val mutableTomorrowOrYesterday = MutableLiveData<String>(null)

    fun getTodayTomorrowCurrency(date: LocalDate){
        viewModelScope.launch {
            var listFirstCurrency = repository.getTodayCurrency(DateConvert.formatForRequest(date))
            var listSecondCurrency = repository
                .getTomorrowOrYesterdayCurrency(DateConvert.formatForRequest(date.plusDays(1)))
            mutableTomorrowOrYesterday.postValue( DateConvert.
            formatForView(date.plusDays(1)))
            if(listSecondCurrency.isEmpty()){
                listSecondCurrency = repository
                    .getTomorrowOrYesterdayCurrency(DateConvert.
                    formatForRequest(date.minusDays(1)))
                mutableTomorrowOrYesterday.postValue( DateConvert.
                formatForView(date.minusDays(1)))
            }

            val currencyEntities =  RoomDb.getDatabase()
                .currencyDao().getCurrencies()

            listFirstCurrency.forEach {
                if(!currencyEntities.first{ entity -> entity.curId == it.curAbbreviation}.checked)
                    listFirstCurrency.toMutableList().remove(it)
            }

            val absoluteList = listOf(listFirstCurrency,listSecondCurrency)
            mutableListCurrency.postValue(absoluteList)
        }
    }

}