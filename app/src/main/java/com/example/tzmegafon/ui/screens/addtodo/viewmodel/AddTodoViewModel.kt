package com.example.tzmegafon.ui.screens.addtodo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.data.locale.repositorydb.RepositoryTodo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val todo: RepositoryTodo
) :ViewModel() {
    suspend fun insertTodo(todoModel:TodoModel) = todo.insertMedicine(todoModel)
}