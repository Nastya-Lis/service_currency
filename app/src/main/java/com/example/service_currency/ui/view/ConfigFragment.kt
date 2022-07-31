package com.example.service_currency.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.service_currency.R
import com.example.service_currency.data.db.CurrencyEntity
import com.example.service_currency.ui.viewmodel.ConfigViewModel
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

class ConfigFragment : Fragment() {

    private val viewModel: ConfigViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getCurrency("2022-04-29", requireContext())
        return ComposeView(requireContext()).apply {
            setContent { ConfigScreen() }
        }
    }


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun ConfigScreen() {
        var data = remember { mutableStateOf( viewModel.listStateCurrency )}
        return Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Настройка валют") },
                    navigationIcon = {
                        IconButton(onClick = { findNavController().popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_navigate_before_48),
                                contentDescription = "back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.updateCurrenciesInDb(
                                requireContext(),
                                viewModel.listStateCurrency
                            )
                            findNavController().popBackStack()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_check_48),
                                contentDescription = "save"
                            )
                        }
                    }
                )
            })
        {
            var data1 = remember { mutableStateOf( viewModel.listStateCurrency )}
            val stateLazy = rememberReorderableLazyListState(
                onMove = { from, to ->
                    viewModel.listStateCurrency =
                        viewModel.listStateCurrency.toMutableList().apply {
                            add(to.index, removeAt(from.index))
                            //Log.i("mem", from.index.toString())
                        }

                    viewModel.listStateCurrency[from.index].position = to.index
                    //Log.i("meme",viewModel.listStateCurrency.indexOf(from.key).toString())
                }
            )
            LazyColumn(
                state = stateLazy.listState,
                modifier = Modifier
                    .reorderable(stateLazy)
                    .detectReorderAfterLongPress(stateLazy)
            ) {
                items(items = viewModel.listStateCurrency, { it.curId }) { item ->
                    ReorderableItem(stateLazy, key = item) { isDragging ->
                        val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 1.5.dp)
                                .shadow(elevation.value)
                            //verticalAlignment = AlignmentVertical.Center,
                        ) {
                            var itemState by remember {
                                mutableStateOf(
                                    Pair(
                                        mutableStateOf(item.checked),
                                        mutableStateOf(item.position)
                                    )
                                )
                            }

                            Column(Modifier.weight(3f)) {
                                LaunchedEffect(item) {
                                    Log.d("Hui", item.curId)
                                }
                                Text(item.curId, fontWeight = FontWeight.Bold)
                                Text("${item.scale}  ${item.name}")
                            }
                            Box(modifier = Modifier.weight(1.5f)){
                                Switch(checked = itemState.first.value, onCheckedChange = {
                                    itemState.first.value = it
                                    viewModel.listStateCurrency.get(itemState.second.value).checked =
                                        itemState.first.value
                                })
                            }

                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_view_headline_24),
                                contentDescription = "",
                                modifier = Modifier.paddingFromBaseline(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    /*   private val viewModel: ConfigViewModel by viewModels()
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
                           viewModel.mutableListConfigCurrency.value as MutableList*//*listOfShit*//*)
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }

        viewModel.getCurrency("2022-04-08", requireContext())

        return view
    }*/

}