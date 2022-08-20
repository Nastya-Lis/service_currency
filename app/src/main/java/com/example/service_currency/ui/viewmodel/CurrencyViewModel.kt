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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository

    val mutableListCurrency = MutableLiveData<List<List<Currency>>>(null)
    val mutableTomorrowOrYesterday = MutableLiveData<String>(null)

    fun getTodayTomorrowCurrency(date: LocalDate) {
        viewModelScope.launch {
            var listFirstCurrency: MutableList<Currency> =
                repository.getTodayCurrency(DateConvert.formatForRequest(date)).toMutableList()
            var listSecondCurrency: MutableList<Currency> = repository
                .getTomorrowOrYesterdayCurrency(DateConvert.formatForRequest(date.plusDays(1)))
                .toMutableList()
            mutableTomorrowOrYesterday.postValue(
                DateConvert.formatForView(date.plusDays(1))
            )
            if (listSecondCurrency.isEmpty()) {
                listSecondCurrency = repository
                    .getTomorrowOrYesterdayCurrency(
                        DateConvert.formatForRequest(date.minusDays(1))
                    ).toMutableList()
                mutableTomorrowOrYesterday.postValue(
                    DateConvert.formatForView(date.minusDays(1))
                )
            }


            /*listFirstCurrency.toMutableList().forEach {
            if(!currencyEntities.first{ entity -> entity.curId == it.curAbbreviation}.checked){
               // mmm.remove(it)
            listFirstCurrency.toMutableList().remove(it)
            }

        }*/

            /*  listSecondCurrency.forEach {
                 if(!currencyEntities.first{ entity -> entity.curId == it.curAbbreviation}.checked)
                     //mmmm.remove(it)
                     listSecondCurrency.toMutableList().remove(it)
             }*/

            val rr = doList(listFirstCurrency)
            val rrr = doList(listSecondCurrency)

            val absoluteList = listOf(rr, rrr)
            mutableListCurrency.postValue(absoluteList)
        }
    }

    private suspend fun doList(collection: MutableList<Currency>): MutableList<Currency> {
        val currencyEntities = RoomDb.getDatabase()
            .currencyDao().getCurrencies()

        var mutableIterator = collection.iterator()
        var mutableIteratorId = currencyEntities.iterator()

        while (mutableIterator.hasNext() && mutableIteratorId.hasNext()){
            var current = mutableIterator.next()
            var currentEntity = mutableIteratorId.next()
            current.curPosition = currentEntity.position
        }
        collection.sortBy { it.curPosition }

        //var smth = collection[collection.indexOf(mutableIterator.next())]
       // collection.sortBy { it.curAbbreviation == mutableIteratorId.next() }

     /*   while (mutableIterator.hasNext() && mutableIteratorId.hasNext()) {
            var current = mutableIterator.next()
            var currentEntity = mutableIteratorId.next()
            var indexCurr = collection.indexOf(current)
            var indexCurrID = sortedCurrenciesId[currencyEntities.indexOf(currentEntity)].position

            if(current.curAbbreviation != currentEntity.curId && indexCurr != indexCurrID){
                val beforeVal = collection[indexCurr]
                collection[indexCurr] = collection[indexCurrID]
                collection[indexCurrID] = beforeVal
            }
            else if(indexCurr == indexCurrID) {
                collection[indexCurr] = collection[current.curPosition]
                //collection[indexCurr] = collection.first { it.curAbbreviation == currentEntity.curId }
            }
        }*/

        collection.removeIf {
            !currencyEntities.first { entity -> entity.curId == it.curAbbreviation }.checked
        }
        return collection
    }

}