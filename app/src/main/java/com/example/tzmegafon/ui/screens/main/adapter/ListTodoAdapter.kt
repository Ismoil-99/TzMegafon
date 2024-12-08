package com.example.tzmegafon.ui.screens.main.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tzmegafon.R
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.databinding.ListTodoBinding

class ListTodoAdapter(private val allTodos: MutableList<TodoModel>,private val onItemClicked: (Int) -> Unit,
): ListAdapter<TodoModel,ListTodoAdapter.HistoryViewHolder>(FinishDiffUtil()) {

    class FinishDiffUtil : DiffUtil.ItemCallback<TodoModel>(){
        override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
            return oldItem == newItem
        }
    }


    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListTodoBinding.bind(view)
        fun bind(todoModel: TodoModel){
            binding.apply {
                titleTodo.text = todoModel.nameTodo
                descTodo.text = todoModel.descTodo
                dataTodo.text = todoModel.dateTodo
                if (!todoModel.activeTodo){
                    binding.statusTodo.text = "выполнена"
                    binding.statusTodo.setBackgroundColor(
                        ContextCompat.getColor(root.context.applicationContext,
                            R.color.red))}
                else{
                    binding.statusTodo.text = "активна"
                    binding.statusTodo.setBackgroundColor(
                        ContextCompat.getColor(root.context.applicationContext,
                            R.color.green))}
                if (todoModel.audioNameTodo.isNotEmpty()){
                    binding.iconMusicTodo.visibility = View.VISIBLE
                }
                }
                Glide.with(binding.root.context)
                    .load(Uri.parse(todoModel.pathImageTodo))
                    .transform(RoundedCorners(10))
                    .placeholder(R.drawable.baseline_insert_photo)
                    .into(binding.imageTodo)
            binding.root.setOnClickListener {
                onItemClicked.invoke(todoModel.id)
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
        val item = allTodos[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = allTodos.size
}