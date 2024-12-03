package com.example.tzmegafon.ui.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

        viewModel.getAllTodo().observe(viewLifecycleOwner){todo ->
            lifecycleScope.launch {
                setupAdapter(todo)
            }
        }
    }

    private fun setupAdapter(todo: MutableList<TodoModel>?) {
        recyclerView = binding.todoList
        val adapter = ListTodoAdapter(todo!!){
        }
        recyclerView.adapter = adapter
        binding.todoList.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        recyclerView.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}