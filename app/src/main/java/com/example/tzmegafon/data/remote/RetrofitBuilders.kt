package com.example.tzmegafon.data.remote

import com.example.tzmegafon.data.remote.repository.TodoRepository
import com.example.tzmegafon.data.remote.requests.TodoRequest
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://my-json-server.typicode.com/Ismoil-99/fake/"

@Module
@InstallIn(SingletonComponent::class)
object ApiDoru {
    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofitClient: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun getRetrofitRequest() : TodoRequest = retrofitClient.create(TodoRequest::class.java)
}