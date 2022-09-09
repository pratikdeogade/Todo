package com.example.todoapp.repository

import com.example.todoapp.network.NetworkState
import com.example.todoapp.network.models.Tasks
import com.example.todoapp.network.models.TasksItem

class FakeTaskRepository : TaskRepository {

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }



    override suspend fun getTasks(): NetworkState<List<TasksItem>> {
        val tasks = Tasks()
        return if (!shouldReturnNetworkError) {
            NetworkState.Success(tasks)
        } else {
            NetworkState.NetworkError
        }

    }
}