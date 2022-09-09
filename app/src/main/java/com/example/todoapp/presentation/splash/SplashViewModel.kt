package com.example.todoapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private var timeMutableSharedFlow = MutableSharedFlow<Int>()
    val _timeSharedFlow = timeMutableSharedFlow.asSharedFlow()

    private var time = MutableStateFlow<Int>(5)
    val _time = time.asStateFlow()

    init {
        collectflow()
    }

    val countdownflow = flow {
        val startValue = 5
        var currentValue = startValue
        while (currentValue > 0) {
            delay(1000)
            currentValue--
            emit(currentValue)
        }
    }


    private fun collectflow() {
        viewModelScope.launch {
            countdownflow.collect {
                timeMutableSharedFlow.emit(it)
                time.emit(it)
            }
        }
    }
}
