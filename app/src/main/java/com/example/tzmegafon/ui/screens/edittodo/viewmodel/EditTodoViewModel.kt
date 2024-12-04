package com.example.tzmegafon.ui.screens.edittodo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.data.locale.repositorydb.RepositoryTodo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val todo: RepositoryTodo
) :ViewModel() {

    fun getTodobyId(id:Int) = todo.getItembyId(id)

    fun updateTodo(todoModel: TodoModel) = todo.updateTodo(todoModel)
}