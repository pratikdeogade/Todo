package com.example.todoapp.repository

import com.example.todoapp.network.NetworkState
import com.example.todoapp.network.TaskService
import com.example.todoapp.network.models.TasksItem
import com.example.todoapp.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TaskRepository_Impl(
    private val taskService: TaskService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskRepository {

    override suspend fun getTasks(): NetworkState<List<TasksItem>> {
       return safeApiCall(dispatcher){
           taskService.getTasks()
       }
    }
}