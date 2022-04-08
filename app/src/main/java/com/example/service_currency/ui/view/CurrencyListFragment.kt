package com.example.service_currency.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.service_currency.R
import com.example.service_currency.adapter.RecyclerCurrencyAdapter
import com.example.service_currency.data.converter.DateConvert
import com.example.service_currency.ui.viewmodel.CurrencyViewModel
import com.google.android.material.appbar.MaterialToolbar
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


class CurrencyListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_currency_list, container, false)

        val todayDate: TextView = view.findViewById(R.id.today_date)
        val tomorrowOrYesterdayDate: TextView = view.findViewById(R.id.tomorrow_or_yesterday_date)

        val recycler: RecyclerView = view.findViewById(R.id.currency_recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())


        viewModel.mutableListCurrency.observe(requireActivity()){
            if(it != null){
                recycler.adapter = RecyclerCurrencyAdapter(it)
                recycler.adapter?.notifyDataSetChanged()
            }
        }

        viewModel.mutableTomorrowOrYesterday.observe(requireActivity()){
            tomorrowOrYesterdayDate.text = it
        }

        view.findViewById<Toolbar>(R.id.topAppBar).setOnMenuItemClickListener {
            when (it.itemId) {

                R.id.configuration_setting -> {
                    findNavController().navigate(R.id.action_currencyListFragment_to_configFragment)
                    true
                }
                else -> false
            }
        }


        viewModel.getTodayTomorrowCurrency(LocalDate.now())

        todayDate.text = DateConvert.formatForView(LocalDate.now())

     return view
    }

 /*   override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){

        R.id.configuration_setting -> {
            Log.d("meme", "woooork")
            findNavController().navigate(R.id.action_currencyListFragment_to_configFragment)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }*/


/*: Boolean{
        if(item.itemId == R.id.configuration_setting){
            findNavController().navigate(R.id.action_currencyListFragment_to_configFragment)
        }
        return super.onOptionsItemSelected(item)
    }*/



}