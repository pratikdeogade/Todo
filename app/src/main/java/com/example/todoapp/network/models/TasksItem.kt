package com.example.todoapp.network.models

import com.google.gson.annotations.SerializedName

data class TasksItem(
    @SerializedName("completed")
    val completed: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
)