package com.example.todoapp.presentation.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.todoapp.getOrAwaitValueTest
import com.example.todoapp.network.NetworkState
import com.example.todoapp.network.models.Tasks
import com.example.todoapp.network.models.TasksItem
import com.example.todoapp.repository.FakeTaskRepository
import com.example.todoapp.repository.TaskRepository_Impl
import com.example.todoapp.utils.MainCoroutineRule
import com.example.todoapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when` as When
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TaskListViewModelMockitoTest {

    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()// for livedata

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: FakeTaskRepository

    @Mock
    lateinit var mockObserver: Observer<Resource<List<TasksItem>>>

    private lateinit var taskListViewModel: TaskListViewModel


    @Before
    fun setup() {
        taskListViewModel = TaskListViewModel(repository)
        taskListViewModel._task.observeForever(mockObserver)
    }

    @Test
    fun `fetch getTask network call, return success`() {
        val task = Tasks()
        mainCoroutineRule.runBlockingTest {
            When(repository.getTasks()).thenAnswer {
                NetworkState.Success(task)
            }
            taskListViewModel.fetchGetTasks()
            val result = taskListViewModel._task.value
            verify(mockObserver, times(1)).onChanged(Resource.loading())
            verify(mockObserver).onChanged(Resource.success(task))
            verify(mockObserver, never()).onChanged(Resource.error("No Network"))
            assertEquals(Resource.success(emptyList<Tasks>()), result)
        }
    }


    @Test
    fun `fetch getTask network call, return error`() {
        val task = Tasks()
        mainCoroutineRule.runBlockingTest {
            When(repository.getTasks()).thenAnswer {
                NetworkState.NetworkError
            }

            taskListViewModel.fetchGetTasks()
            val result = taskListViewModel._task.value
            verify(mockObserver, times(1)).onChanged(Resource.loading())
            verify(mockObserver).onChanged(Resource.error("No Network"))
            verify(mockObserver, never()).onChanged(Resource.success(task))
            assertNotNull(result)
            assertEquals(Resource.error<String>("No Network"), result)
        }
    }
}


