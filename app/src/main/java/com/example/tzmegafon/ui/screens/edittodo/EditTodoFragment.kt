package com.example.tzmegafon.ui.screens.edittodo

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tzmegafon.R
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.databinding.FragmentAddTodoBinding
import com.example.tzmegafon.databinding.FragmentEditTodoBinding
import com.example.tzmegafon.ui.screens.addtodo.UploadImageDialog
import com.example.tzmegafon.ui.screens.addtodo.viewmodel.AddTodoViewModel
import com.example.tzmegafon.ui.screens.edittodo.viewmodel.EditTodoViewModel
import com.example.tzmegafon.ui.screens.main.viewmodel.MainTodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class EditTodoFragment : Fragment() {

    private var _binding: FragmentEditTodoBinding? = null

    private val binding get() = _binding!!
    private val args: EditTodoFragmentArgs by navArgs()
    private val viewModel : EditTodoViewModel by viewModels()
    private var dataTodo = MutableStateFlow<String>("")
    private var statusTodo = MutableStateFlow<Boolean>(false)
    private var imageTodo = MutableStateFlow<String>("")
    private val calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listTodo = listOf("aктивна","выполнена")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, listTodo)
        binding.statusTodo.setAdapter(adapter)
        binding.statusTodo.setOnItemClickListener { adapterView, view, i, l ->
            lifecycleScope.launch {
                if (i == 0){
                    statusTodo.emit(true)
                }else if (i == 1 ){
                    statusTodo.emit(false)
                }
            }
        }
        binding.dateValue.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(), {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd.MM.yyyy.", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)
                    binding.dateValue.text = formattedDate
                    lifecycleScope.launch {
                        dataTodo.emit(formattedDate)
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        binding.addTodo.setOnClickListener {
            if (imageTodo.value == ""){
                Toast.makeText(requireContext(),R.string.error_image,Toast.LENGTH_SHORT).show()
            }else if (binding.nameTodo.text.toString().isEmpty()){
                Toast.makeText(requireContext(),R.string.error_name,Toast.LENGTH_SHORT).show()
            }else if (binding.descTextValue.text.toString().isEmpty()){
                Toast.makeText(requireContext(),R.string.error_desc,Toast.LENGTH_SHORT).show()
            }else if (dataTodo.value == ""){
                Toast.makeText(requireContext(),R.string.error_data,Toast.LENGTH_SHORT).show()
            }else{
                lifecycleScope.launch {
                    val todo = TodoModel(
                        id = args.id,
                        nameTodo = binding.nameTodo.text.toString(),
                        descTodo = binding.descTextValue.text.toString(),
                        dateTodo = dataTodo.value,
                        activeTodo = statusTodo.value,
                        pathImageTodo = imageTodo.value
                    )
                    viewModel.updateTodo (
                        todo
                    )
                    findNavController().navigateUp()
                    //Toast.makeText(requireContext(),R.string.success,Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.saveImage.setOnClickListener {
            val dialog = UploadImageDialog{ file, uri ->
                lifecycleScope.launch {
                    imageTodo.emit(uri.toString())
                }
                Glide.with(this)
                    .load(uri)
                    .into(binding.iconTodo)

            }
            dialog.show(requireActivity().supportFragmentManager, "SELECTIMAGE")
        }
        viewModel.getTodobyId(args.id).observe(viewLifecycleOwner){ todo ->
            Glide.with(requireContext())
                .load(Uri.parse(todo.pathImageTodo))
                .into(binding.iconTodo)
            binding.nameTodo.setText(todo.nameTodo)
            binding.descTextValue.setText(todo.descTodo)
            binding.dateValue.text = todo.dateTodo
            if (todo.activeTodo){
                binding.statusTodo.setText(adapter.getItem(0),false)
            }else{
                binding.statusTodo.setText(adapter.getItem(1),false)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}