package com.example.service_currency.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.service_currency.R
import com.example.service_currency.data.db.CurrencyEntity
import com.example.service_currency.data.db.RoomDb
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecyclerConfigCurrencyAdapter(/*private var currencies: List<CurrencyEntity>?,*/private val context: Context):
    RecyclerView.Adapter<RecyclerConfigCurrencyAdapter.ViewHolder>() {


    private val differResult = object: DiffUtil.ItemCallback<CurrencyEntity>(){
        override fun areItemsTheSame(oldItem: CurrencyEntity, newItem: CurrencyEntity): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CurrencyEntity, newItem: CurrencyEntity): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differResult)


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val abbreviation =  view.findViewById<TextView>(R.id.abbreviation_config)
        val decryption =  view.findViewById<TextView>(R.id.decryption_config)
        val switch = view.findViewById<SwitchMaterial>(R.id.switch_config)
        val drag = view.findViewById<ImageButton>(R.id.drag)
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

        val room : RoomDb = RoomDb.getDatabase(context)

        val currency = differ.currentList[position]

        holder.abbreviation.text = currency?.curId
        holder.decryption.text = currency?.scale.toString() + " " + currency?.name
        holder.switch.isChecked = if(currency != null) currency!!.checked else true

       // currency.position = holder.bindingAdapterPosition

        holder.switch.setOnCheckedChangeListener{
            bouncedButton, isChecked ->
            currency?.checked = isChecked
//            GlobalScope.launch(Dispatchers.IO) {
//                room.currencyDao().updateCurrency(currency!!)
//            }
        }

    }


    fun moveItem(fromPosition: Int, toPosition: Int) {
        val list = differ.currentList.toMutableList()
        val fromItem = list[fromPosition]
        list.removeAt(fromPosition)
        if (toPosition < fromPosition) {
            //list[fromPosition].position = toPosition
            list.add(toPosition + 1 , fromItem)

        } else {
           // list[fromPosition].position = toPosition
            list.add(toPosition - 1, fromItem)
        }
        differ.submitList(list)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}