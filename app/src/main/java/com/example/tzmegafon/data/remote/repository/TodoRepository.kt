package com.example.tzmegafon.data.remote.repository

import com.example.tzmegafon.data.remote.model.TodoModelRemote
import com.example.tzmegafon.data.remote.model.UIState
import kotlinx.coroutines.flow.Flow

interface TodoRepository {


    fun getTodo(): Flow<UIState<List<TodoModelRemote>>>
}