package com.hfad.tasks.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.tasks.data.TaskDao
import kotlinx.coroutines.launch

class EditTaskViewModel(taskId: Long, val dao: TaskDao): ViewModel() {
    //receive the current Task
    val task = dao.get(taskId)

    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    fun updateTask() {
        viewModelScope.launch {
            dao.update(task.value!!)
            _navigateToList.value = true
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            dao.delete(task.value!!)
            _navigateToList.value = true
        }
    }

    fun onNavigateToList() {
        _navigateToList.value = false
    }
}
