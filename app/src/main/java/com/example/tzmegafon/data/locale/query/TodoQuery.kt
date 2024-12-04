package com.example.tzmegafon.data.locale.query

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tzmegafon.data.locale.model.TodoModel

@Dao
interface TodoQuery {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoModel: TodoModel)

    @Query("Select * from todo")
    fun gelAllTodo(): LiveData<MutableList<TodoModel>>

    @Delete
    suspend fun deleteTodo(todoModel: TodoModel)

    @Query("Select * from todo where active = :active")
    fun getActiveTodo(active: Int) : LiveData<MutableList<TodoModel>>

    @Query("Select * from todo where id = :id")
    fun getTodobyId(id: Int) : LiveData<TodoModel>

    @Update()
    fun updateTodo(todoModel: TodoModel)
}