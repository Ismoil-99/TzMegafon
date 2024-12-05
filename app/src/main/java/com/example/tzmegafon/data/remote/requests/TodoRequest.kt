package com.example.tzmegafon.data.remote.requests

import com.example.tzmegafon.data.remote.model.TodoModelRemote
import retrofit2.Response
import retrofit2.http.GET

interface TodoRequest {
    @GET("todos")
    suspend fun getTodo(
    ): Response<List<TodoModelRemote>>
}