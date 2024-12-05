package com.example.tzmegafon.data.remote.repository

import com.example.tzmegafon.data.remote.model.TodoModelRemote
import kotlinx.coroutines.flow.Flow

interface TodoRepository {


    fun getTodo(): Flow<List<TodoModelRemote>>
}