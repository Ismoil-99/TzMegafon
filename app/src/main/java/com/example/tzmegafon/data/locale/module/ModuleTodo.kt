package com.example.tzmegafon.data.locale.module

import android.app.Application
import com.example.tzmegafon.data.locale.db.DataBaseCore
import com.example.tzmegafon.data.locale.query.TodoQuery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getAppDatabase(context: Application): DataBaseCore {
        return DataBaseCore.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun getAppDao(appDatabase: DataBaseCore): TodoQuery {
        return appDatabase.saveTodo()
    }

}