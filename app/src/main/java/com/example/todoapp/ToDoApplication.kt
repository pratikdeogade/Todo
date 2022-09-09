package com.example.todoapp

import android.app.Application
import com.example.todoapp.repository.TaskRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToDoApplication:Application() {

    val taskRepository: TaskRepository
        get() = ServiceLocator.provideRepository()
}