package com.example.todoapp.presentation.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.HomeFragmentBinding
import com.example.todoapp.presentation.splash.SplashFragment
import com.example.todoapp.utils.Resource
import com.example.todoapp.utils.getViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var adapter: TaskListDataBindingAdapter
  //  private val viewModel: TaskListViewModel by viewModels()
    private val viewModel: TaskListViewModel by viewModels{
        getViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecylceView()
        setUpObservers()
        setUpRetry()
    }

    private fun setUpRecylceView() {
        adapter = TaskListDataBindingAdapter()
        binding.todoList.layoutManager = LinearLayoutManager(requireContext())
        binding.todoList.adapter = adapter
    }

    private fun setUpObservers() {
        viewModel.fetchGetTasks()
        viewModel._task.observe(viewLifecycleOwner) { taskList ->
            taskList.let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.error.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        adapter.setItems(ArrayList(it.data))
                    }
                    Resource.Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        binding.error.visibility = View.VISIBLE
                    }
                    Resource.Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.error.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setUpRetry() {
        binding.retry.setOnClickListener { viewModel.fetchGetTasks() }
    }


}