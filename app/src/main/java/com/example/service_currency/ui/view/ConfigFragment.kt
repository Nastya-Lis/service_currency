package com.example.service_currency.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.service_currency.R
import com.example.service_currency.adapter.RecyclerConfigCurrencyAdapter
import com.example.service_currency.ui.viewmodel.ConfigViewModel

class ConfigFragment : Fragment() {

    private val viewModel: ConfigViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_config, container, false)

        val recycler: RecyclerView = view.findViewById(R.id.config_currency_recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())


        viewModel.mutableListConfigCurrency.observe(requireActivity()){
            if(it != null){
                recycler.adapter = RecyclerConfigCurrencyAdapter(it)
                recycler.adapter?.notifyDataSetChanged()
            }
        }



        viewModel.getCurrency("2022-03-30")

        return view
    }

}