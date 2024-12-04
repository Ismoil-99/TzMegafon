package com.example.tzmegafon.data.locale.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.data.locale.query.TodoQuery

@Database(entities = [TodoModel::class,
], version = 2, exportSchema = false)
abstract class DataBaseCore : RoomDatabase() {

    abstract fun saveTodo(): TodoQuery

    companion object{
        private var DB_INSTANCE: DataBaseCore? = null
        fun getAppDBInstance(context: Context): DataBaseCore {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseCore::class.java,
                    "db"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }
}