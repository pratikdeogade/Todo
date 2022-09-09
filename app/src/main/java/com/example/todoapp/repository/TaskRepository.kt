package com.example.todoapp.repository

import com.example.todoapp.network.NetworkState
import com.example.todoapp.network.models.TasksItem

interface TaskRepository {

    suspend fun getTasks(): NetworkState<List<TasksItem>>
}
