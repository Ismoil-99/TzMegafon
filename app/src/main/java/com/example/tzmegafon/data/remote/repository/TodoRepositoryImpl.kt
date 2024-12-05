package com.example.tzmegafon.data.remote.repository

import android.util.Log
import androidx.lifecycle.asFlow
import com.example.tzmegafon.App
import com.example.tzmegafon.data.locale.model.TodoModel
import com.example.tzmegafon.data.locale.query.TodoQuery
import com.example.tzmegafon.data.locale.repositorydb.RepositoryTodo
import com.example.tzmegafon.data.remote.model.TodoModelRemote
import com.example.tzmegafon.data.remote.requests.TodoRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import retrofit2.HttpException

class TodoRepositoryImpl @Inject constructor(
    private val todoRequest: TodoRequest,
    private val repositoryTodo: RepositoryTodo
) : TodoRepository {

    private val saveStatus = App.sharedPreferencesEditor
    override fun getTodo(): Flow<List<TodoModelRemote>> {
        return flow {
            try {
                val response = todoRequest.getTodo()
                if (response.isSuccessful) {
                    if (response.body() != null && response.code() == 200) {
                                response!!.body()!!.map {
                                    repositoryTodo.insertMedicine(
                                        TodoModel(
                                            it.id,
                                            it.name,
                                            it.desc,
                                            it.date,
                                            it.active,
                                            it.pathImage,
                                            it.audioPath,
                                            it.audioName
                                        )
                                    )
                                }
                                saveStatus.putString("SAVESTATUS","1").apply()
                        Log.d("value1","${response.body()}")
                    } else {
                        Log.d("value2","${response.code()}")
                    }
                } else {
                    Log.d("value3","${response.code()}")
                }
            } catch (e: HttpException) {
                Log.d("value4","${e.message}")
            } catch (e: Throwable) {
                Log.d("value5","${e.message}")
            }
        }
    }
}