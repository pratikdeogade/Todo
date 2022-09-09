package com.example.todoapp.presentation.tasks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.network.models.TasksItem

class TaskListAdapter() : RecyclerView.Adapter<TaskViewHolder>() {

    private val items = ArrayList<TasksItem>()

    fun setItems(items: ArrayList<TasksItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding: ItemTaskBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) =
        holder.bind(items[position])
}

class TaskViewHolder(private val itemBinding: ItemTaskBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: TasksItem) {
        itemBinding.taskTiltle.text = item.title
        itemBinding.taskStatus.text = if (item.completed) "completed" else "inComplete"
        itemBinding.taskStatus.setTextColor(if (item.completed) Color.GREEN else Color.RED)
    }
}

