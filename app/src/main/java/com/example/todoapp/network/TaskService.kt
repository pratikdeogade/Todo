package com.example.todoapp.network

import com.example.todoapp.di.NetworkModule
import com.example.todoapp.network.models.Tasks
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TaskService {

    @GET("todos")
    suspend fun getTasks(): Tasks


    companion object {
        fun create(): TaskService {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl(NetworkModule.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(TaskService::class.java)
        }
    }
}











