package com.example.todoapp.utils

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.todoapp.ToDoApplication
import com.example.todoapp.presentation.ViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository =
        (requireContext().applicationContext as ToDoApplication).taskRepository
    return ViewModelFactory(repository)
}


