package com.example.tzmegafon.data.locale.query

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tzmegafon.data.locale.model.TodoModel

@Dao
interface TodoQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoModel: TodoModel)

    @Query("Select * from todo")
    fun gelAllTodo(): LiveData<MutableList<TodoModel>>

}