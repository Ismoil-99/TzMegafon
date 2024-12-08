package com.example.tzmegafon.ui.screens.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tzmegafon.App
import com.example.tzmegafon.R
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.data.remote.model.UIState
import com.example.tzmegafon.databinding.FragmentMainBinding
import com.example.tzmegafon.ui.screens.edittodo.EditTodoFragmentDirections
import com.example.tzmegafon.ui.screens.main.adapter.ListTodoAdapter
import com.example.tzmegafon.ui.screens.main.filterbottom.FilterListDialog
import com.example.tzmegafon.ui.screens.main.viewmodel.MainTodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel : MainTodoViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private var idFilter = MutableLiveData(0)
    private var filterTodos = mutableListOf<TodoModel>()
    private var inc = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val status = App.sharedPreferences.getString("SAVESTATUS","0")
        val saveIdFilter = App.sharedPreferencesEditor
        val deleleteFilter = App.sharedPreferences.edit()
        if (status == "0"){
            lifecycleScope.launch {
                viewModel.getTodo().collectLatest { todo ->
                    when(todo){
                        is UIState.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                            binding.todoList.visibility = View.GONE
                        }
                        is UIState.Success -> {
                            binding.loading.visibility = View.GONE
                            binding.todoList.visibility = View.VISIBLE
                            Toast.makeText(requireContext(),getString(R.string.success_data),Toast.LENGTH_SHORT).show()
                        }
                        is UIState.Error -> {}
                    }
                }
            }
        }
        binding.addTodo.setOnClickListener {
            viewModel.sortTodo(-1)
            findNavController().navigate(R.id.to_add_todo)
        }
        viewModel.sortTodo(-1)
        binding.filterBox.setOnClickListener {

            val showFilter = FilterListDialog{ ids ,name ->
                binding.nameSort.text = name
                binding.iconSot.visibility = View.VISIBLE
                binding.iconSotDown.visibility = View.GONE
                lifecycleScope.launch {
                    idFilter.postValue(ids)
                }
                if (ids == 1){
                    viewModel.sortTodo(1)
                }else if (ids == 2){
                    viewModel.sortTodo(0)
                }

                saveIdFilter.putInt("ID",ids).apply()
            }
            showFilter.show(requireActivity().supportFragmentManager, "SELECTIMAGE")
        }
        binding.iconSot.setOnClickListener {
            deleleteFilter.remove("ID").apply()
            lifecycleScope.launch {
                idFilter.postValue(0)
            }
            binding.nameSort.text = "фильтр"
            binding.iconSot.visibility = View.GONE
            binding.iconSotDown.visibility = View.VISIBLE
            viewModel.sortTodo(-1)
        }

        viewModel.sortTodo.observe(viewLifecycleOwner){

                    setupAdapter(it)

        }
    }

    private fun setupAdapter(todosList:List<TodoModel>,) {
        filterTodos.clear()
        for ((inc, list) in todosList.withIndex()){
            filterTodos.add(
                inc, TodoModel(
                    id = list.id,
                    nameTodo = list.nameTodo,
                    descTodo = list.descTodo,
                    dateTodo = list.dateTodo,
                    activeTodo = list.activeTodo,
                    pathImageTodo = list.pathImageTodo,
                    audioPathTodo = list.audioPathTodo,
                    audioNameTodo = list.audioNameTodo
                )
            )
        }
        recyclerView = binding.todoList
        val adapter = ListTodoAdapter(filterTodos) { id ->
            val destination = EditTodoFragmentDirections.toEdit(id)
            findNavController().navigate(destination)
            viewModel.sortTodo(-1)
        }
        recyclerView.adapter = adapter
        binding.todoList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        adapter.submitList(filterTodos)
        recyclerView.setHasFixedSize(true)
        val swipe = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val item = filterTodos[pos]
                lifecycleScope.launch {
                    viewModel.deleteTodo(item)
                }
            }
        })
        swipe.attachToRecyclerView(null)
        swipe.attachToRecyclerView(binding.todoList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val deleleteFilter = App.sharedPreferences.edit()
        deleleteFilter.remove("ID").apply()
        lifecycleScope.launch {
            idFilter.postValue(0)
        }
    }
}