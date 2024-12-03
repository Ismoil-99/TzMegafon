package com.example.tzmegafon.ui.screens.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.data.locale.repositorydb.RepositoryTodo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainTodoViewModel @Inject constructor(
    private val todo: RepositoryTodo
):ViewModel() {

    fun getAllTodo() = todo.getAllTodo()

    suspend fun deleteTodo(todoModel: TodoModel) = todo.deleteTodo(todoModel)
    fun activeTodo(active:Int) = todo.getActiveTodo(active)

}