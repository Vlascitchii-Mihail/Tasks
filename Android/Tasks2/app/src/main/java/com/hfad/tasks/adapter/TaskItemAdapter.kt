package com.hfad.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hfad.tasks.data.Task
import com.hfad.tasks.databinding.TaskItemBinding

class TaskItemAdapter(val clickListener: (taskId: Long) -> Unit) : ListAdapter<Task, TaskItemAdapter.TaskItemViewHolder>(
    TaskDiffItemCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder =
        TaskItemViewHolder.inflateFrom(parent)


    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {

        //getItem(position) - return the current element from ListAdapter's background list
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class TaskItemViewHolder(val binding: TaskItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task, clickListener: (taskId: Long) -> Unit) {
            binding.task = item

            //send the click listener
            binding.root.setOnClickListener { clickListener(item.taskId) }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }
        }
    }


}