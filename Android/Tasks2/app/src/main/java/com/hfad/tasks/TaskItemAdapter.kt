package com.hfad.tasks

import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter : RecyclerView.Adapter() {
    var data = listOf<Task>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
}