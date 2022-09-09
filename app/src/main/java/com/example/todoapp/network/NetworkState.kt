package com.example.todoapp.network

sealed class NetworkState<out T> {
    data class Success<out T>(val value: T): NetworkState<T>()
    data class GenericError(val code: Int? = null, val error: String? = null): NetworkState<Nothing>()
    object NetworkError: NetworkState<Nothing>()
}
