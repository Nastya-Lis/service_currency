package com.example.service_currency.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.service_currency.R
import com.example.service_currency.adapter.RecyclerConfigCurrencyAdapter
import com.example.service_currency.data.db.CurrencyEntity
import com.example.service_currency.ui.viewmodel.ConfigViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ConfigFragment : Fragment() {

    private val viewModel: ConfigViewModel by viewModels()
    var listOfShit: List<CurrencyEntity> = listOf()


    private val itemTouchHelper by lazy {
        val itemTouchCallback = object: ItemTouchHelper.SimpleCallback(UP or DOWN,0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val recyclerviewAdapter = recyclerView.adapter as RecyclerConfigCurrencyAdapter
                val fromPosition = viewHolder.absoluteAdapterPosition
                val toPosition = target.absoluteAdapterPosition
               // Collections.swap(listOfShit, fromPosition,toPosition )
                recyclerviewAdapter.moveItem(fromPosition, toPosition)
                recyclerviewAdapter.notifyItemMoved(fromPosition,toPosition)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        }
        ItemTouchHelper(itemTouchCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_config, container, false)

        val recycler: RecyclerView = view.findViewById(R.id.config_currency_recycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        itemTouchHelper.attachToRecyclerView(recycler)
        val recyclerAdapter = RecyclerConfigCurrencyAdapter(requireContext())

        viewModel.mutableListConfigCurrency.observe(requireActivity()){
            if(it != null){
                //listOfShit = it
                recyclerAdapter.differ.submitList(it as MutableList)
                recycler.adapter = recyclerAdapter
                recycler.adapter?.notifyDataSetChanged()
            }
        }


        view.findViewById<Toolbar>(R.id.topAppBar).setOnMenuItemClickListener {
            when (it.itemId) {

                R.id.accept_setting -> {
                    viewModel.updateCurrenciesInDb(requireContext(),
                        viewModel.mutableListConfigCurrency.value as MutableList/*listOfShit*/)
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }

        viewModel.getCurrency("2022-04-08", requireContext())

        return view
    }

}