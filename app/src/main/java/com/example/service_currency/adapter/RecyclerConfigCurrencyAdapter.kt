package com.example.service_currency.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.service_currency.R
import com.example.service_currency.data.model.Currency

class RecyclerConfigCurrencyAdapter(private val currencies: List<Currency>?):
    RecyclerView.Adapter<RecyclerConfigCurrencyAdapter.ViewHolder>() {


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val abbreviation =  view.findViewById<TextView>(R.id.abbreviation_config)
        val decryprion =  view.findViewById<TextView>(R.id.decryption_config)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.card_currency_config, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currency = currencies?.get(position)

        holder.abbreviation.text = currency?.curAbbreviation
        holder.decryprion.text = currency?.curScale.toString() + " " + currency?.curName


        //holder.itemView.onDragEvent()

    }

    override fun getItemCount(): Int {
        return currencies?.size ?: 0
    }
}