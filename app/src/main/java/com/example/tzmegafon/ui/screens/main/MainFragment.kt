package com.example.tzmegafon.ui.screens.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tzmegafon.R
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.databinding.FragmentMainBinding
import com.example.tzmegafon.ui.screens.edittodo.EditTodoFragmentDirections
import com.example.tzmegafon.ui.screens.main.adapter.ListTodoAdapter
import com.example.tzmegafon.ui.screens.main.viewmodel.MainTodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MainTodoViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private var items = MutableStateFlow<List<TodoModel>>(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTodo.setOnClickListener {
            findNavController().navigate(R.id.to_add_todo)
            binding.activeTodo.isChecked = false
            binding.successActive.isChecked = false

        }
        viewModel.getAllTodo().observe(viewLifecycleOwner){
            lifecycleScope.launch {
                items.emit(it)
            }
        }
        setupAdapter()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun setupAdapter() {
        recyclerView = binding.todoList
        val adapter = ListTodoAdapter(){ id ->

            val destination = EditTodoFragmentDirections.toEdit(id)
            findNavController().navigate(destination)
            binding.activeTodo.isChecked = false
            binding.successActive.isChecked = false

        }
        recyclerView.adapter = adapter
        binding.todoList.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val item =  adapter.differ.currentList[pos]
                lifecycleScope.launch {
                    viewModel.deleteTodo(item)
                }
            }
        }).attachToRecyclerView(binding.todoList)
        binding.activeTodo.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                viewModel.activeTodo(1).observe(viewLifecycleOwner){
                    lifecycleScope.launch {
                        items.emit(it)
                    }
                }
            }else{
                viewModel.getAllTodo().observe(viewLifecycleOwner) { todo ->
                    lifecycleScope.launch {
                        items.emit(todo)
                    }
                }
            }

        }
        binding.successActive.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                viewModel.activeTodo(0).observe(viewLifecycleOwner){
                    lifecycleScope.launch {
                        items.emit(it)
                    }
                }
            }else{
                viewModel.getAllTodo().observe(viewLifecycleOwner) { todo ->
                    lifecycleScope.launch {
                        items.emit(todo)
                    }
                }
            }

        }

        lifecycleScope.launch {
           items.collectLatest {
               adapter.differ.submitList(it)
           }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}