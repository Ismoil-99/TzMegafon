package com.example.tzmegafon.ui.screens.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.tzmegafon.App
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.data.locale.repositorydb.RepositoryTodo
import com.example.tzmegafon.data.remote.repository.TodoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainTodoViewModel @Inject constructor(
    private val todo: RepositoryTodo,
    private val todoRepositoryImpl: TodoRepositoryImpl
):ViewModel() {

    private val saveStatus = App.sharedPreferencesEditor

    fun getAllTodo() = todo.getAllTodo()

    suspend fun deleteTodo(todoModel: TodoModel) = todo.deleteTodo(todoModel)
    fun activeTodo(active:Int) = todo.getActiveTodo(active)


    fun getTodo() = todoRepositoryImpl.getTodo()

}