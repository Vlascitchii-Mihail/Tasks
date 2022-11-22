package com.hfad.tasks

import androidx.recyclerview.widget.DiffUtil

class TaskDiffItemCallback: DiffUtil.ItemCallback<Task>() {

    //starts first
    override fun areItemsTheSame(oldItem: Task, newItem: Task) =
        (oldItem.taskId == newItem.taskId)

    //starts second if the areItemsTheSame() returned true
    //checks if elements have equal content
    override fun areContentsTheSame(oldItem: Task, newItem: Task) =
        (oldItem == newItem)
}