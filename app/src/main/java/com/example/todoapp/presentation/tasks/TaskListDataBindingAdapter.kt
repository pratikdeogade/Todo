package com.example.todoapp.presentation.tasks


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.BR
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskDatabindingBinding
import com.example.todoapp.network.models.TasksItem


class TaskListDataBindingAdapter() : RecyclerView.Adapter<TaskViewDataBindingHolder>() {

    private val items = ArrayList<TasksItem>()

    fun setItems(items: ArrayList<TasksItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewDataBindingHolder {
        val binding: ItemTaskDatabindingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_task_databinding, parent, false
        )
        return TaskViewDataBindingHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TaskViewDataBindingHolder, position: Int) {
        holder.bind(items[position])
    }

}
class TaskViewDataBindingHolder(private val itemBinding: ItemTaskDatabindingBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(item: TasksItem) {
        itemBinding.setVariable(BR.taskItem, item)
        itemBinding.executePendingBindings()

    }
}

