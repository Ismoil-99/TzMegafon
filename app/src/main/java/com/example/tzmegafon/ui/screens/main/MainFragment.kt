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
import com.example.tzmegafon.ui.screens.main.adapter.ListTodoAdapter
import com.example.tzmegafon.ui.screens.main.viewmodel.MainTodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MainTodoViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView

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
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        setupAdapter()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun setupAdapter() {
        recyclerView = binding.todoList
        val adapter = ListTodoAdapter(){
        }
        recyclerView.adapter = adapter
        binding.todoList.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        recyclerView.setHasFixedSize(true)
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
                    Log.d("value","$it")
                    adapter.notifyDataSetChanged()
                    adapter.differ.submitList(it)
                }
            }else{
                viewModel.getAllTodo().observe(viewLifecycleOwner) { todo ->
                    adapter.notifyDataSetChanged()
                    adapter.differ.submitList(todo)
                }
            }

        }
        binding.successActive.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                viewModel.activeTodo(0).observe(viewLifecycleOwner){
                    Log.d("value","$it")
                    adapter.notifyDataSetChanged()
                    adapter.differ.submitList(it)
                }
            }else{
                viewModel.getAllTodo().observe(viewLifecycleOwner) { todo ->
                    adapter.notifyDataSetChanged()
                    adapter.differ.submitList(todo)
                }
            }

        }
        if (binding.successActive.isChecked){
            Log.d("value","true")
        }else{
            viewModel.getAllTodo().observe(viewLifecycleOwner) { todo ->
                adapter.notifyDataSetChanged()
                adapter.differ.submitList(todo)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}