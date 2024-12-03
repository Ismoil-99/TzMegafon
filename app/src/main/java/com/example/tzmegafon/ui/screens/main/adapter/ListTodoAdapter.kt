package com.example.tzmegafon.ui.screens.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tzmegafon.R
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.databinding.ListTodoBinding

class ListTodoAdapter(
    private val historyList: List<TodoModel>, private val onItemClicked: (String) -> Unit,
): RecyclerView.Adapter<ListTodoAdapter.HistoryViewHolder>() {


    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListTodoBinding.bind(view)
        fun bind(todoModel: TodoModel){
            binding.apply {
                titleTodo.text = todoModel.nameTodo
                descTodo.text = todoModel.descTodo
                dataTodo.text = todoModel.dateTodo
                Glide.with(binding.root.context)
                    .load(Uri.parse(todoModel.pathImageTodo))
                    .into(binding.imageTodo)

            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_todo, parent, false)
        return HistoryViewHolder(layout)
    }
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.bind(item)
    }
    override fun getItemCount() = historyList.size
}