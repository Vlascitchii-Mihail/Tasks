package com.hfad.tasks.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.tasks.data.TaskDao

class EditTaskViewModelFactory(private val taskId: Long, private val dao: TaskDao)
    : ViewModelProvider.Factory {

        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
                return EditTaskViewModel(taskId, dao) as T
            }

            throw IllegalArgumentException("Unknown ViewModel")
        }
}