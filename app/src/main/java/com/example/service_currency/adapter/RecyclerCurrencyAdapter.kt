package com.example.service_currency.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.service_currency.R
import com.example.service_currency.data.model.Currency


class RecyclerCurrencyAdapter(private val currencies: List<List<Currency>?>?):
    RecyclerView.Adapter<RecyclerCurrencyAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val abbreviation =  view.findViewById<TextView>(R.id.abbreviation)
        val decryprion =  view.findViewById<TextView>(R.id.decryption)
        val first_course =  view.findViewById<TextView>(R.id.first_course)
        val second_course =  view.findViewById<TextView>(R.id.second_course)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.card_currency_list, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val firstListCurrency = currencies?.get(0)
        val secondListCurrency = currencies?.get(1)

        val currencyFirst = firstListCurrency?.get(position)
        val currencySecond = secondListCurrency?.get(position)

      //  Log.d("TAG33", "why: ${currencies?.get(1)?.get(2)?.curName}")

        holder.abbreviation.text = currencyFirst?.curAbbreviation
        holder.decryprion.text = currencyFirst?.curScale.toString() + " " + currencyFirst?.curName
        holder.first_course.text = currencyFirst?.curOfficialRate.toString()
        holder.second_course.text = currencySecond?.curOfficialRate.toString()

    }

    override fun getItemCount(): Int {
        return  currencies?.get(0)?.size ?: 0
    }

}