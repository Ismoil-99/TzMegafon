package com.example.tzmegafon.data.locale.repositorydb

import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.data.locale.query.TodoQuery
import javax.inject.Inject

class RepositoryTodo @Inject constructor (private val todoQuery: TodoQuery) {

    fun getAllTodo() = todoQuery.gelAllTodo()

    suspend fun insertMedicine(todoModel: TodoModel) = todoQuery.insertTodo(todoModel)

}