package com.hfad.tasks.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hfad.tasks.data.Task

class TaskDiffItemCallback: DiffUtil.ItemCallback<Task>() {

    //starts first
    override fun areItemsTheSame(oldItem: Task, newItem: Task) =
        (oldItem.taskId == newItem.taskId)

    //starts second if the areItemsTheSame() returned true
    //checks if elements have equal content
    override fun areContentsTheSame(oldItem: Task, newItem: Task) =
        (oldItem == newItem)
}