package com.example.tzmegafon.ui.screens.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


    var sortTodo = MutableLiveData<MutableList<TodoModel>>()

    fun getAllTodo() = todo.getAllTodo()

    suspend fun deleteTodo(todoModel: TodoModel) = todo.deleteTodo(todoModel)
    fun activeTodo(active:Int) = todo.getActiveTodo(active)

    fun sortTodo(int: Int){
        when (int) {
            0 -> {
                viewModelScope.launch {
                    todo.getActiveTodo(0).asFlow().collectLatest {
                        Log.d("value1","${it}")
                        sortTodo.postValue(it)
                    }
                }
            }
            1 -> {
                viewModelScope.launch {
                    todo.getActiveTodo(1).asFlow().collectLatest {
                        Log.d("value2","${it}")
                        sortTodo.postValue(it)
                    }
                }
            }
            else -> {
                viewModelScope.launch {
                    todo.getAllTodo().asFlow().collectLatest {
                        Log.d("value","${it}")
                        sortTodo.postValue(it)
                    }
                }
            }
        }
    }

    fun getTodo() = todoRepositoryImpl.getTodo()

}