package com.example.todoapp.utils

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapterUtils {
    @JvmStatic
    @BindingAdapter("app:taskStatus")
    fun taskStatus(view: TextView, status: Boolean) {
        view.text = if (status) "Completed" else "InComplete"
        view.setTextColor(if (status) Color.GREEN else Color.RED)
    }
}