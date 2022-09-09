package com.example.todoapp.utils

import com.example.todoapp.network.NetworkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): NetworkState<T> {
    return withContext(dispatcher) {
        try {
            NetworkState.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> NetworkState.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    NetworkState.GenericError(code, errorResponse)
                }
                else -> {
                    NetworkState.GenericError(null, null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return throwable.response()?.errorBody().toString()

}

