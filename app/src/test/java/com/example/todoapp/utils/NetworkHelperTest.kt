package com.example.todoapp.utils

import com.example.todoapp.network.NetworkState
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class NetworkHelperTest {

    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `when lambda returns successfully then it should emit the result as success`() {
        runTest {
            val lambdaResult = true
            val result = safeApiCall(dispatcher) { lambdaResult }
            assertEquals(NetworkState.Success(lambdaResult), result)
        }
    }

    @Test
    fun `when lambda throws IOException then it should emit the result as NetworkError`() {
        runTest {
            val result = safeApiCall(dispatcher) { throw IOException() }
            assertEquals(NetworkState.NetworkError, result)
        }
    }

    @Test
    fun `when lambda throws HttpException then it should emit the result as GenericError`() {
        //val errorBody = "{\"errors\": [\"Unexpected parameter\"]}".toResponseBody("application/json")
        runTest {
            val result = safeApiCall(dispatcher) {
                throw HttpException(Response.error<Any>(422, null))
            }
            assertEquals(NetworkState.GenericError(null,null), result)
        }
    }

    @Test
    fun `when lambda throws unknown exception then it should emit GenericError`() {
        runTest {
            val result = safeApiCall(dispatcher) {
                throw IllegalStateException()
            }
            assertEquals(NetworkState.GenericError(), result)
        }
    }
}

