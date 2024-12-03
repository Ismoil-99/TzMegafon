package com.example.tzmegafon.data.locale.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo",)
data class TodoModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    val nameTodo: String,
    @ColumnInfo(name = "desc")
    val descTodo:String,
    @ColumnInfo(name = "date")
    val dateTodo:String,
    @ColumnInfo(name = "active")
    val activeTodo:Boolean,
    @ColumnInfo(name = "pathImage")
    val pathImageTodo:String,
    )