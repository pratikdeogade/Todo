package com.example.todoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.presentation.tasks.TaskListViewModel
import com.example.todoapp.repository.TaskRepository

class ViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(TaskListViewModel::class.java) -> TaskListViewModel(repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
                    as T
        }
    }
}