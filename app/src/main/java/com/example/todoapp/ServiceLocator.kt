package com.example.todoapp

import com.example.todoapp.network.TaskService
import com.example.todoapp.repository.TaskRepository
import com.example.todoapp.repository.TaskRepository_Impl

object ServiceLocator {

    var repository: TaskRepository? = null

    fun provideRepository(): TaskRepository {
        return repository ?: createRepository()
    }

    private fun createRepository(): TaskRepository {
        val newRepo = TaskRepository_Impl(TaskService.create())
        repository = newRepo
        return newRepo
    }

}