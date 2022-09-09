package com.example.todoapp.presentation.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todoapp.utils.MainCoroutineRule
import com.example.todoapp.getOrAwaitValueTest
import com.example.todoapp.network.models.Tasks
import com.example.todoapp.repository.FakeTaskRepository
import com.example.todoapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.*
import org.junit.Assert.*


@ExperimentalCoroutinesApi
class TaskListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()// for livedata

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var taskListViewModel: TaskListViewModel
    private val repository = FakeTaskRepository()

    @Before
    fun setup() {
        taskListViewModel = TaskListViewModel(repository)
    }

    @Test
    fun `fetch getTask network call, return loading,success`() {
        runTest {
            taskListViewModel.fetchGetTasks()
            val resultLoading = taskListViewModel._task.getOrAwaitValueTest(customValue = Resource.loading())
            assertNotNull(resultLoading)
            assertEquals(resultLoading, Resource.loading(null))
            val resultSuccess = taskListViewModel._task.getOrAwaitValueTest(customValue = Resource.success(emptyList()))
            assertNotNull(resultSuccess)
            assertEquals(resultSuccess, Resource.success(emptyList<Tasks>()))
        }

    }

    @Test
    fun `fetch getTask network call, return success`() {
        runTest {
           taskListViewModel.fetchGetTasks()
            val result = taskListViewModel._task.getOrAwaitValueTest()
            assertNotNull(result)
            assertEquals(result, Resource.success(emptyList<Tasks>()))
        }
    }

    @Test
    fun `fetch getTask network call, return network error`() {
        runTest {
            repository.setShouldReturnNetworkError(true)
            taskListViewModel.fetchGetTasks()
            val result = taskListViewModel._task.getOrAwaitValueTest()
            assertEquals(result, Resource.error("No Network", null))
        }
    }
}

