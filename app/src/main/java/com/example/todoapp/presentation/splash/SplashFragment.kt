package com.example.todoapp.presentation.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope

import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.SplashFragmentBinding
import com.example.todoapp.utils.collectLatestLifecycleFlow
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask

class SplashFragment : Fragment() {

    private lateinit var binding: SplashFragmentBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTimerForSplash()
    }

    private fun startTimerForSplash() {

//        Timer().schedule(timerTask {
//            findNavController().navigate(R.id.action_spalsh_to_home)
//        }, 5000)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel._timeSharedFlow.collect {
                    if (it == 0) {
                        findNavController().navigate(R.id.action_spalsh_to_home)
                    }
                }
            }
        }
    }
}