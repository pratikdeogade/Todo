package com.example.todoapp.presentation.tasks

import androidx.lifecycle.*
import com.example.todoapp.network.NetworkState
import com.example.todoapp.network.models.TasksItem
import com.example.todoapp.repository.TaskRepository
import com.example.todoapp.utils.Resource
import kotlinx.coroutines.launch

class TaskListViewModel (private val taskRepository: TaskRepository) :
    ViewModel() {

    private var task = MutableLiveData<Resource<List<TasksItem>>>()
    val _task: LiveData<Resource<List<TasksItem>>> = task

    fun fetchGetTasks() {
        task.postValue(Resource.loading())
        viewModelScope.launch {
            val response = taskRepository.getTasks()
            when (response) {
                is NetworkState.Success -> {
                    task.postValue(Resource.success(response.value))
                }
                is NetworkState.NetworkError -> {
                    task.postValue(Resource.error("No Network"))
                }
            }
        }
    }

    fun filterByStatus(status: TaskStatus) {
        when (status) {
            TaskStatus.COMPLETED -> {
                task.value = task.value?.data?.let {
                    Resource.success(it.filter {
                        it.completed
                    })
                }
            }
            TaskStatus.INCOMPLETE -> {
                task.value = task.value?.data?.let {
                    Resource.success(it.filter {
                        !it.completed
                    })
                }
            }
        }
    }
}